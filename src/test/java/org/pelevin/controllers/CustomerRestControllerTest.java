package org.pelevin.controllers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pelevin.CustomerApplication;
import org.pelevin.model.CustomerVO;
import org.pelevin.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by dmitry on 30.08.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CustomerApplication.class)
@WebAppConfiguration
public class CustomerRestControllerTest {

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(),
			Charset.forName("utf8"));

	private MockMvc mockMvc;

	private HttpMessageConverter mappingJackson2HttpMessageConverter;

	@Autowired
	private CustomerRepository customerRepository;

	private List<CustomerVO> customerList = new ArrayList<>();

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	void setConverters(HttpMessageConverter<?>[] converters) {

		this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream().filter(
				hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();

		Assert.assertNotNull("the JSON message converter must not be null",
				this.mappingJackson2HttpMessageConverter);
	}

	@Before
	public void setup() throws Exception {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();

		this.customerRepository.deleteAll();

		this.customerList.add(customerRepository.save(new CustomerVO("Peter", "abc", "123")));
		this.customerList.add(customerRepository.save(new CustomerVO("Mike", "xyz", "456")));
	}

	@Test
	public void readSingleCustomer() throws Exception {
		mockMvc.perform(get("/customers/"
				+ this.customerList.get(0).getId()))
				.andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.id", is(this.customerList.get(0).getId())))
				.andExpect(jsonPath("$.name", is("Peter")))
				.andExpect(jsonPath("$.address", is("abc")))
				.andExpect(jsonPath("$.telephoneNumber", is("123")));
	}

	@Test
	public void readCustomers() throws Exception {
		mockMvc.perform(get("/customers"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].id", is(this.customerList.get(0).getId())))
				.andExpect(jsonPath("$[0].name", is("Peter")))
				.andExpect(jsonPath("$[0].address", is("abc")))
				.andExpect(jsonPath("$[0].telephoneNumber", is("123")))
				.andExpect(jsonPath("$[1].id", is(this.customerList.get(1).getId())))
				.andExpect(jsonPath("$[1].name", is("Mike")))
				.andExpect(jsonPath("$[1].address", is("xyz")))
				.andExpect(jsonPath("$[1].telephoneNumber", is("456")));
	}

	@Test
	public void createCustomer() throws Exception {
		String customerJson = json(new CustomerVO("David", "aaa", "111"));
		this.mockMvc.perform(post("/customers")
				.contentType(contentType)
				.content(customerJson))
				.andExpect(status().isCreated());
	}

	protected String json(Object o) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.mappingJackson2HttpMessageConverter.write(
				o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}

	@Test
	public void deleteCustomer() throws Exception {
		mockMvc.perform(delete("/customers/"
				+ this.customerList.get(0).getId()))
				.andExpect(status().isOk());
	}

	@Test
	public void updateCustomer() throws Exception {
		String customerJson = json(new CustomerVO("David", "aaa", "111"));
		this.mockMvc.perform(put("/customers/"
				+ this.customerList.get(0).getId())
				.contentType(contentType)
				.content(customerJson))
				.andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.id", is(this.customerList.get(0).getId())))
				.andExpect(jsonPath("$.name", is("David")))
				.andExpect(jsonPath("$.address", is("aaa")))
				.andExpect(jsonPath("$.telephoneNumber", is("111")));
	}

	@Test
	public void customerNotFound() throws Exception {
		mockMvc.perform(get("/customers/123"))
				.andExpect(status().isNotFound());
	}

}
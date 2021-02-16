package com.project.primenumbersservice.request.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PrimesControllerTest {

    @Autowired
    private PrimesController primesController;
    @Autowired
    private MockMvc mvc;


    @Test
    public void shouldReturnCorrectPrimesWithPositiveInput() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/primes/10").accept("*/*"))
                .andExpect(request().asyncStarted())
                .andDo(MockMvcResultHandlers.log())
                .andReturn();

        mvc.perform(MockMvcRequestBuilders.asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string(equalTo(getResponse("10", "[2,3,5,7]"))));
    }

    @Test
    public void shouldReturnNoPrimesWithZeroInput() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/primes/0").accept("*/*"))
                .andExpect(request().asyncStarted())
                .andDo(MockMvcResultHandlers.log())
                .andReturn();

        mvc.perform(MockMvcRequestBuilders.asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string(equalTo(getResponse("0", "[]"))));
    }

    @Test
    public void shouldReturnNoPrimesWithNegativeInput() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/primes/-10").accept("*/*"))
                .andExpect(request().asyncStarted())
                .andDo(MockMvcResultHandlers.log())
                .andReturn();

        mvc.perform(MockMvcRequestBuilders.asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string(equalTo(getResponse("-10", "[]"))));
    }

    @Test
    public void shouldThrowError404NotFoundWithNoInput() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/primes/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldThrowError400BadRequestWithNonNumericInput() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/primes/abc")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldThrowError405MethodNotAllowedWithRequestTypeOtherThanGet() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/primes/10")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void shouldReturnCorrectFormatAndResponseWithXmlMediaType() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/primes/3").accept("application/xml"))
                .andExpect(request().asyncStarted())
                .andDo(MockMvcResultHandlers.log())
                .andReturn();

        mvc.perform(MockMvcRequestBuilders.asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_XML))
                .andExpect(content().string(equalTo("<Primes>\n" +
                        "  <initial>3</initial>\n" +
                        "  <primes>\n" +
                        "    <primes>2</primes>\n" +
                        "    <primes>3</primes>\n" +
                        "  </primes>\n" +
                        "</Primes>")));
    }

    private static String getResponse(String initialNum, String primeNumbers) {
        return "{\"Initial\":" + initialNum + ",\"Primes\":" + primeNumbers + "}";
    }
}
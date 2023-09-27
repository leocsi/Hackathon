package com.example.DialBank;

import com.example.DialBank.model.Account;
import com.example.DialBank.repository.AccountRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles({"test"})
class DialBankApplicationTests {

    private static final String ACCOUNT_ENDPOINT_URL = "/api";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private AccountRepository accountRepository;


	@Test
	@DirtiesContext
	public void testDeposit_success() throws Exception {

		//Testing data
		Long testId = 99999L;
		String first_name = "X";
		String last_name = "Y";
		Float balance = 100000000f;
		String phone_number = "1234567";
		Float amount = 11111111f;

		String JSON = addAccount(new Account(testId,first_name,last_name,balance,phone_number));
		Account testAccount = objectMapper.readValue(JSON, Account.class);
		Long id = testAccount.getUser_id();
		Float balanceBefore = testAccount.getBalance();

		this.mockMvc.perform(put(ACCOUNT_ENDPOINT_URL + "/account/deposit/" + id + "-" + amount))
				.andDo(print())
				.andExpect(status().is2xxSuccessful());

		String JSONAfter = getAccountById(id);

		Account accountAfter = objectMapper.readValue(JSONAfter, Account.class);
		deleteAccount(id);

		Float balanceAfter = accountAfter.getBalance();
		assertEquals(balanceBefore + amount, balanceAfter);
	}

	@Test
	@DirtiesContext
	public void testWithdraw_success() throws Exception {

		Long testId = 99999L;
		String first_name = "X";
		String last_name = "Y";
		Float balance = 100000000f;
		String phone_number = "1234567";
		Float amount = 11111f;



		String JSON = addAccount(new Account(testId,first_name,last_name,balance,phone_number));
		Account testAccount = objectMapper.readValue(JSON, Account.class);
		Long id = testAccount.getUser_id();
		Float balanceBefore = testAccount.getBalance();

		this.mockMvc.perform(put(ACCOUNT_ENDPOINT_URL + "/account/withdraw/" + id + "-" + amount))
				.andDo(print())
				.andExpect(status().is2xxSuccessful());

		String JSONAfter = getAccountById(id);

		Account accountAfter = objectMapper.readValue(JSONAfter, Account.class);
		deleteAccount(id);

		Float balanceAfter = accountAfter.getBalance();
		assertEquals(balanceBefore - amount, balanceAfter);
	}


    @Test
    @DirtiesContext
    public void testDELETE_Success() throws Exception {
        //  Given DB was populated by mysql
        Account tempAccount = new Account(1L, "name1", "name2", 100f, "07463527");

        String result = this.mockMvc.perform(post(ACCOUNT_ENDPOINT_URL + "/account/add")
                        .header("Content-Type", "application/json")
                        .content(tempAccount.json()))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Account account = objectMapper.readValue(result, Account.class);
        long id = account.getUser_id();
        List<Account> beforeDelete = getAllAccounts();

        // When
        this.mockMvc.perform(delete(ACCOUNT_ENDPOINT_URL + "/account/delete/" + id)
                        .header("Content-Type", "application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Then
        List<Account> afterDelete = getAllAccounts();
        assertEquals(afterDelete.size(), beforeDelete.size() - 1);
    }

    @Test
	@DirtiesContext
    public void testDELETE_Failure() throws Exception {
        //  Given DB was populated by mysql
        Account tempAccount = new Account(5000L, "name1", "name2", 100f, "07463527");

        String result = this.mockMvc.perform(post(ACCOUNT_ENDPOINT_URL + "/account/add")
                        .header("Content-Type", "application/json")
                        .content(tempAccount.json()))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Account account = objectMapper.readValue(result, Account.class);
        long id = account.getUser_id();
        List<Account> beforeDelete = getAllAccounts();

        // When
        this.mockMvc.perform(delete(ACCOUNT_ENDPOINT_URL + "/account/delete/" + id+1)
                        .header("Content-Type", "application/json"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<Account> afterDelete = getAllAccounts();
        assertEquals(afterDelete.size(), beforeDelete.size());
		deleteAccount(id);
    }

    private List<Account> getAllAccounts() throws Exception {
        String JSON = mockMvc.perform(get(ACCOUNT_ENDPOINT_URL + "/accounts"))
                .andReturn()
                .getResponse()
                .getContentAsString();
        return objectMapper.readValue(JSON, new TypeReference<>() {
        });
    }

	private String getAccountById(Long id) throws Exception {
		return this.mockMvc.perform(get(ACCOUNT_ENDPOINT_URL + "/account/" + id))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString();
	}

	private String addAccount(Account account) throws Exception {

		String JSONToAdd = objectMapper.writeValueAsString(account);

		return this.mockMvc.perform(post(ACCOUNT_ENDPOINT_URL + "/account/add")
						.header("Content-Type", "application/json")
						.content(JSONToAdd))
				.andDo(print())
				.andExpect(status().is2xxSuccessful())
				.andReturn()
				.getResponse()
				.getContentAsString();
	}

	private void deleteAccount(Long id) throws Exception {

		this.mockMvc.perform(delete(ACCOUNT_ENDPOINT_URL + "/account/delete/" + id))
				.andDo(print())
				.andExpect(status().is2xxSuccessful())
				.andReturn()
				.getResponse()
				.getContentAsString();
	}
}


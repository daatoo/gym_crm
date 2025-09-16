package security;



import com.gymcrm.security.AuthStatusController;
import com.gymcrm.security.BruteForceProtectionService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AuthStatusControllerTest {

    @Test
    void status_returnsBlockedAndSeconds() throws Exception {
        BruteForceProtectionService brute = Mockito.mock(BruteForceProtectionService.class);
        when(brute.isBlocked("alice")).thenReturn(true);
        when(brute.secondsUntilUnlock("alice")).thenReturn(120L);

        MockMvc mvc = MockMvcBuilders.standaloneSetup(new AuthStatusController(brute)).build();

        mvc.perform(get("/auth/status").param("username", "alice"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.blocked", is(true)))
                .andExpect(jsonPath("$.secondsUntilUnlock", is(120)));
    }
}

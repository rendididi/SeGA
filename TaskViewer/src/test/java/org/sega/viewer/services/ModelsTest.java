package org.sega.viewer.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Collection;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.sega.viewer.models.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.sega.viewer.models.Process;
/**
 *
 * @author: Raysmond
 */
@RunWith(MockitoJUnitRunner.class)
public class ModelsTest {

    @Mock
    private IModel model;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void modelShouldnotBeNull() {

        assertThat(model != null);
    }

}


package com.caveofprogramming.socialnetwork.configuration;


import com.caveofprogramming.socialnetwork.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    //The following field is for Encryption and Decryption
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/",
                            "/about",
                            "/register",
                            "/registrationconfirmed",
                            "/invaliduser",
                            "/expiredtoken",
                            "/verifyemail",
                            "/confirmregister")
                    .permitAll()
                    .antMatchers(
                        "/js/*",
                        "/css/*",
                        "/img/*")
                    .permitAll()
                    /**With the following antMatchers we are giving the permission
                     * for the ADMIN role to have the access to pages, which are
                     * restricted for USER role*/
                    .antMatchers("/addstatus",
                            "/editstatus",
                            "/deletestatus",
                            "/viewstatus")
                    .hasRole("ADMIN")
                    .antMatchers("/profile",
                            "/profile/*",
                            "/edit-profile-about",
                            "/upload-profile-photo",
                            "/profilephoto/*")
                .authenticated()
                    .anyRequest()
                    .denyAll()
                    .and()
                .formLogin()
                    .loginPage("/login")
                    .defaultSuccessUrl("/")
                    .permitAll()
                    .and()
                .logout()
                    .permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth
                .inMemoryAuthentication()
                .withUser("john")
                .password("hello")
                .roles("USER");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        //Starting from .passwordEncoder we are applying encryption and decryption
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

}

package com.saascrm.application.auth;

import com.saascrm.domain.company.Company;
import com.saascrm.domain.user.User;
import com.saascrm.infrastructure.persistence.CompanyRepository;
import com.saascrm.infrastructure.persistence.UserRepository;
import com.saascrm.infrastructure.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(
            CompanyRepository companyRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtTokenProvider jwtTokenProvider
    ) {
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Transactional
    public String register(RegisterRequest request) {
        Company company = new Company();
        company.setName(request.companyName());
        company.setEmail(request.companyEmail());
        company.setPlan(request.plan());
        // createdAt será preenchido por trigger ou no serviço de criação de tenant em versão futura

        company = companyRepository.save(company);

        User user = new User();
        user.setName(request.userName());
        user.setEmail(request.userEmail());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole("OWNER");
        user.setCompany(company);

        userRepository.save(user);

        return jwtTokenProvider.generateToken(user);
    }

    public String login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        var principal = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        var user = userRepository.findByEmail(principal.getUsername())
                .orElseThrow();

        return jwtTokenProvider.generateToken(user);
    }
}


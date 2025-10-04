package app.cultivapp.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {
    private final PasswordEncoder encoder;
    public PasswordService(PasswordEncoder encoder) { this.encoder = encoder; }
    public String hash(String raw) { return encoder.encode(raw); }
    public boolean matches(String raw, String hash) { return encoder.matches(raw, hash); }
}

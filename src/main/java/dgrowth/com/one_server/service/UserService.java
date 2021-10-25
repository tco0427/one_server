package dgrowth.com.one_server.service;

import dgrowth.com.one_server.dto.request.SignUpRequest;
import dgrowth.com.one_server.dto.response.UserResponse;
import dgrowth.com.one_server.entity.Authority;
import dgrowth.com.one_server.entity.User;
import dgrowth.com.one_server.repository.UserRepository;
import dgrowth.com.one_server.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long save(User user) {
        validateDuplicateMember(user);
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return user.getId();
    }

    private void validateDuplicateMember(User user) {
        List<User> findUsers = userRepository.findAll();
        for (User findUser : findUsers) {
            if(user.getEmail().equals(findUser.getEmail())) {
                throw new IllegalStateException("이미 존재하는 회원입니다.");
            }
        }
    }

    public Boolean validateMember(User user, String password) {
        String memberPassword = user.getPassword();

        if(!passwordEncoder.matches(password, memberPassword)) {
            return false;
        } else {
            return true;
        }
    }

    public List<User> findMembers() {
        return userRepository.findAll();
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(NoSuchElementException::new);
    }

    @Transactional
    public void update(Long id, String nickname, String password, String profileUrl) {
        User user = userRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);

        user.updateMember(nickname, password, profileUrl);
    }

    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public UserResponse getMemberInfo(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("유저 정보가 없습니다."));

        return new UserResponse(user);
    }

    public UserResponse getMyInfo() {
        User user = userRepository.findByEmail(SecurityUtil.getCurrentEmail().get())
                .orElseThrow(() -> new RuntimeException("유저 정보가 없습니다."));

        return new UserResponse(user);
    }

    @Transactional
    public User signup(SignUpRequest request) {
        if (userRepository.findOneWithAuthoritiesByEmail(request.getEmail()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 잇는 유저입니다.");
        }

        Authority authority = Authority.ROLE_USER;

        User user = new User(passwordEncoder.encode(request.getPassword()), request.getName(),request.getNickname(), request.getBirth(), request.getEmail(), true, authority);

        return userRepository.save(user);
    }

    public Optional<User> getUserWithAuthorities(String email) {
        return userRepository.findOneWithAuthoritiesByEmail(email);
    }

    public Optional<User> getMyUserWithAuthorities() {
        return SecurityUtil.getCurrentEmail().flatMap(userRepository::findOneWithAuthoritiesByEmail);
    }
}
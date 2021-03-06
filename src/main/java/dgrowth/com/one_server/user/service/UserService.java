package dgrowth.com.one_server.user.service;

import dgrowth.com.one_server.user.domain.enumeration.PlatformType;
import dgrowth.com.one_server.user.domain.entity.User;
import dgrowth.com.one_server.auth.domain.entity.UserToken;
import dgrowth.com.one_server.user.domain.repository.UserRepository;
import dgrowth.com.one_server.auth.domain.repository.UserTokenRepository;
import dgrowth.com.one_server.auth.domain.exception.InvalidUserException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserTokenRepository userTokenRepository;

    /**
     * 플랫폼 타입(네이버, 카카오)와 플랫폼 유저 고유 ID로 회원정보 조회
     * @param platformType PlatformType
     * @param platformId String
     * @return User
     */
    public User findByPlatformTypeAndPlatformId(PlatformType platformType, String platformId)
    {
        Optional<User> optionalUser = userRepository.findByPlatformTypeAndPlatformId(platformType, platformId);
        if(optionalUser.isEmpty()){
            return null;
        }

        return optionalUser.get();
    }

    /**
     * 플랫폼 타입(네이버, 카카오)와 플랫폼 유저 고유 ID로 가입한 유저인지 판단
     * @param platformType PlatformType
     * @param platformId String
     * @return boolean
     */
    public boolean isSavedUser(PlatformType platformType, String platformId)
    {
        return userRepository.existsByPlatformTypeAndPlatformId(platformType, platformId);
    }

    /**
     * 회원 정보 저장
     * @param user User
     * @return User
     */
    public User save(User user){
        return userRepository.save(user);
    }

    /**
     * 토큰 정보 저장
     * @param userToken UserToken
     * @return UserToken
     */
    public UserToken saveToken(UserToken userToken){
        return userTokenRepository.save(userToken);
    }

    /**
     * 아이디로 회원 정보 조회
     * @param id Long
     * @return User
     */
    public User findById(Long id) throws InvalidUserException {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty()){
            throw new InvalidUserException();
        }
        return optionalUser.get();
    }
}

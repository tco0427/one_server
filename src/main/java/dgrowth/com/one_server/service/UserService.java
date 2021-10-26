package dgrowth.com.one_server.service;

import dgrowth.com.one_server.data.enumeration.PlatformType;
import dgrowth.com.one_server.domain.entity.User;
import dgrowth.com.one_server.domain.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

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
}

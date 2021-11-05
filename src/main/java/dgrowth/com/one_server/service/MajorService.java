package dgrowth.com.one_server.service;

import dgrowth.com.one_server.data.dto.mapper.GroupMapper;
import dgrowth.com.one_server.data.dto.mapper.MajorMapper;
import dgrowth.com.one_server.data.dto.request.MajorRequest;
import dgrowth.com.one_server.data.dto.response.GroupResponse;
import dgrowth.com.one_server.data.dto.response.MajorResponse;
import dgrowth.com.one_server.data.dto.response.UserResponse;
import dgrowth.com.one_server.domain.entity.Group;
import dgrowth.com.one_server.domain.entity.Major;
import dgrowth.com.one_server.domain.entity.User;
import dgrowth.com.one_server.repository.MajorRepository;
import java.util.NoSuchElementException;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MajorService {

    private final MajorRepository majorRepository;
    private final AuthService authService;
    private final UserService userService;

    public Major findById(Long id) {
        return majorRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Transactional
    public MajorResponse save(MajorRequest majorRequest, HttpServletRequest httpServletRequest) {

        MajorResponse majorResponse = null;

        // 1. 헤더에서 토큰 체크
        String token = authService.getTokenByHeader(httpServletRequest);

        // 2. 토큰 유효성 체크 및 유저 불러오기
        Long hostId = authService.getUserInfoByToken(token).getId();

        // 3. 그룹 생성 및 저장
        Major newMajor = MajorMapper.INSTANCE.requestToEntity(majorRequest);
        Major major = majorRepository.save(newMajor);

        // 4. Response 생성
        majorResponse = MajorMapper.INSTANCE.toDto(major);

        return majorResponse;
    }

    @Transactional
    public Major update(Major major) {
        Major updatedMajor = majorRepository.save(major);
        return updatedMajor;
    }

    public MajorResponse majorInfoById(HttpServletRequest httpServletRequest, Long id) {
        MajorResponse majorResponse = null;

        // 1. 헤더에서 토큰 체크
        String token = authService.getTokenByHeader(httpServletRequest);

        // 2. 토큰 유효성 체크 및 유저 불러오기
        Long userId = authService.getUserInfoByToken(token).getId();

        // 3. 학과 조회
        Major major = findById(id);

        // 4. Response 생성
        majorResponse = MajorMapper.INSTANCE.toDto(major);

        return majorResponse;
    }

    public MajorResponse majorInfoByUserId(HttpServletRequest httpServletRequest) {
        MajorResponse majorResponse = null;

        // 1. 헤더에서 토큰 체크
        String token = authService.getTokenByHeader(httpServletRequest);

        // 2. 토큰 유효성 체크 및 유저 불러오기
        Long userId = authService.getUserInfoByToken(token).getId();

        // 3. 학과 조회
        User user = userService.findById(userId);
        Major major = findById(user.getMajorId());

        // 4. Response 생성
        majorResponse = MajorMapper.INSTANCE.toDto(major);

        return majorResponse;
    }

    // User에 학과 등록을 위한 임시 메소드 -> 학생증 OCR 적용 후 삭제
    @Transactional
    public UserResponse userAddMajor(HttpServletRequest httpServletRequest, Long majorId) {
        UserResponse userResponse = null;

        // 1. 헤더에서 토큰 체크
        String token = authService.getTokenByHeader(httpServletRequest);

        // 2. 토큰 유효성 체크 및 유저 불러오기
        Long userId = authService.getUserInfoByToken(token).getId();

        // 3. 유저 조회
        User user = userService.findById(userId);

        // 4. 유저에 학과 추가
        user.setMajorId(majorId);

        // 5. 유저 저장
        User savedUser = userService.save(user);

        userResponse = savedUser.toResponse();

        return userResponse;
    }
}
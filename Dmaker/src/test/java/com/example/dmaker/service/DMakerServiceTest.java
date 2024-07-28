package com.example.dmaker.service;

import com.example.dmaker.code.StatusCode;
import com.example.dmaker.dto.CreateDeveloper;
import com.example.dmaker.dto.DeveloperDetailDto;
import com.example.dmaker.entity.Developer;
import com.example.dmaker.exception.DMakerErrorCode;
import com.example.dmaker.exception.DMakerException;
import com.example.dmaker.repository.DeveloperRepository;
import com.example.dmaker.repository.RetiredDeveloperRepository;
import com.example.dmaker.type.DeveloperLevel;
import com.example.dmaker.type.DeveloperSkillType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
//@SpringBootTest
class DMakerServiceTest {

    @Mock
    private DeveloperRepository developerRepository;

    @Mock
    private RetiredDeveloperRepository retiredDeveloperRepository;

    //    @Autowired
    @InjectMocks
    private DMakerService dMakerService;

    private final Developer defaultDeveloper = Developer.builder()
            .developerLevel(DeveloperLevel.SENIOR)
            .developerSkillType(DeveloperSkillType.FRONT_END)
            .experienceYears(12)
            .statusCode(StatusCode.EMPLOYED)
            .name("name")
            .age(12)
            .build();
    private final CreateDeveloper.Request defaultCreateRequest = CreateDeveloper.Request.builder()
            .developerLevel(DeveloperLevel.SENIOR)
            .developerSkillType(DeveloperSkillType.FRONT_END)
            .experienceYears(12)
            .memberId("memberId")
            .name("name")
            .age(32)
            .build();
//
//    private CreateDeveloper.Request getCreateRequest(
//            DeveloperLevel developerLevel,
//            DeveloperSkillType developerSkillType,
//            Integer experienceYears
//    ) {
//        return CreateDeveloper.Request.builder()
//                .developerLevel(developerLevel)
//                .developerSkillType(developerSkillType)
//                .experienceYears(experienceYears)
//                .memberId("memberId")
//                .name("name")
//                .age(32)
//                .build();
//    }


    @Test
    public void testSomething() {
        // mocking
        given(developerRepository.findByMemberId(anyString()))
                .willReturn(Optional.of(defaultDeveloper));

        DeveloperDetailDto developerDetail = dMakerService.getDeveloperDetail("memberId");

        assertEquals(DeveloperLevel.SENIOR, developerDetail.getDeveloperLevel());
        assertEquals(12, developerDetail.getExperienceYears());
        assertEquals(DeveloperSkillType.FRONT_END, developerDetail.getDeveloperSkillType());
        /*dMakerService.createDeveloper(CreateDeveloper.Request.builder()
                .developerLevel(DeveloperLevel.SENIOR)
                .developerSkillType(DeveloperSkillType.FRONT_END)
                .experienceYears(12)
                .memberId("memberId")
                .name("name")
                .age(32)
                .build());
        List<DeveloperDto> allEmployedDevelopers = dMakerService.getAllEmployedDevelopers();
        System.out.println("======================");
        System.out.println(allEmployedDevelopers);
        System.out.println("======================");*/

//        String result = "hello" + " world!";
//        assertEquals("hello world!", allEmployedDevelopers);
    }

    @Test
    void createDeveloperTest_success() {
        // given
        given(developerRepository.findByMemberId(anyString()))
                .willReturn(Optional.empty());

        given(developerRepository.save(any(Developer.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        ArgumentCaptor<Developer> captor = ArgumentCaptor.forClass(Developer.class);

        // when
        CreateDeveloper.Response response = dMakerService.createDeveloper(defaultCreateRequest);

        // then
        verify(developerRepository, times(1)).save(captor.capture());

        Developer capturedDeveloper = captor.getValue();
        assertEquals(DeveloperLevel.SENIOR, capturedDeveloper.getDeveloperLevel());
        assertEquals(DeveloperSkillType.FRONT_END, capturedDeveloper.getDeveloperSkillType());
        assertEquals(12, capturedDeveloper.getExperienceYears());

    }

    @Test
    void createDeveloperTest_fail_with_duplicated() {
        // given
        given(developerRepository.findByMemberId(anyString()))
                .willReturn(Optional.of(defaultDeveloper));

//        ArgumentCaptor<Developer> captor = ArgumentCaptor.forClass(Developer.class);
        // when

        // then
        DMakerException dMakerException = assertThrows(DMakerException.class, () -> dMakerService.createDeveloper(defaultCreateRequest));
        assertEquals(DMakerErrorCode.DUPLICATED_MEMBER_ID, dMakerException.getDMakerErrorCode());
    }


}
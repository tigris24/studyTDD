package com.example.dmaker.service;

import com.example.dmaker.code.StatusCode;
import com.example.dmaker.dto.CreateDeveloper;
import com.example.dmaker.dto.DeveloperDetailDto;
import com.example.dmaker.dto.DeveloperDto;
import com.example.dmaker.dto.EditDeveloper;
import com.example.dmaker.entity.Developer;
import com.example.dmaker.entity.RetiredDeveloper;
import com.example.dmaker.exception.DMakerErrorCode;
import com.example.dmaker.exception.DMakerException;
import com.example.dmaker.repository.DeveloperRepository;
import com.example.dmaker.repository.RetiredDeveloperRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DMakerService {
    private final DeveloperRepository developerRepository;

    private final RetiredDeveloperRepository retiredDeveloperRepository;
    @Transactional
    public CreateDeveloper.Response createDeveloper(CreateDeveloper.Request request) {
        validateCreateDeveloperRequest(request);

        Developer developer = createDeveloperFromRequest(request);
        Developer savedDeveloper = developerRepository.save(developer);

        return CreateDeveloper.Response.fromEntity(savedDeveloper);
    }


    private Developer createDeveloperFromRequest(CreateDeveloper.Request request) {
        return Developer.builder()
                .developerLevel(request.getDeveloperLevel())
                .developerSkillType(request.getDeveloperSkillType())
                .experienceYears(request.getExperienceYears())
                .memberId(request.getMemberId())
                .name(request.getName())
                .age(request.getAge())
                .statusCode(StatusCode.EMPLOYED)
                .build();
    }

    private void validateCreateDeveloperRequest(
            @NonNull CreateDeveloper.Request request
    ) {

        request.getDeveloperLevel().validateExperienceYears(
                request.getExperienceYears()
        );
//
//        Optional<Developer> developer = developerRepository.findByMemberId(request.getMemberId());
//        if(developer.isPresent())
//            throw new DMakerException(DMakerErrorCode.DUPLICATED_MEMBER_ID);

        developerRepository.findByMemberId(request.getMemberId())
                .ifPresent((developer -> {
                    throw new DMakerException(DMakerErrorCode.DUPLICATED_MEMBER_ID);
                }));
    }


    @Transactional(readOnly = true)
    public DeveloperDetailDto getDeveloperDetail(String memberId) {
        return DeveloperDetailDto.fromEntity(getDeveloperByMemberId(memberId));
    }

    private Developer getDeveloperByMemberId(String memberId) {
        return developerRepository.findByMemberId(memberId)
                .orElseThrow( () -> new DMakerException(DMakerErrorCode.NO_DEVELOPER));
    }

    @Transactional(readOnly = true)
    public List<DeveloperDto> getAllEmployedDevelopers() {
        return developerRepository.findDevelopersByStatusCodeEquals(StatusCode.EMPLOYED)
                .stream().map(DeveloperDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public DeveloperDetailDto editDeveloper(
            String memberId, EditDeveloper.Request request
    ) {
        request.getDeveloperLevel().validateExperienceYears(request.getExperienceYears());

        return DeveloperDetailDto.fromEntity(
                getUpdatedDeveloperFromRequest(
                        request,
                        getDeveloperByMemberId(memberId)
                )
        );
    }

    private static Developer getUpdatedDeveloperFromRequest(
            EditDeveloper.Request request,
            Developer developer
    ) {
        developer.setDeveloperLevel(request.getDeveloperLevel());
        developer.setDeveloperSkillType(request.getDeveloperSkillType());
        developer.setExperienceYears(request.getExperienceYears());

        return developer;
    }


}

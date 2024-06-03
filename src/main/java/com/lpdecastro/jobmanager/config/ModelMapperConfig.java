package com.lpdecastro.jobmanager.config;

import com.lpdecastro.jobmanager.dto.CandidateDto;
import com.lpdecastro.jobmanager.dto.RecruiterDto;
import com.lpdecastro.jobmanager.entity.Candidate;
import com.lpdecastro.jobmanager.entity.Recruiter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.addMappings(candidateMap());
        modelMapper.addMappings(recruiterMap());

        return modelMapper;
    }

    private PropertyMap<Candidate, CandidateDto> candidateMap() {
        return new PropertyMap<>() {
            @Override
            protected void configure() {
                map().setUsername(source.getUser().getUsername());
                map().setEmail(source.getUser().getEmail());
                map().setCreatedAt(source.getUser().getCreatedAt());
                map().setUpdatedAt(source.getUser().getUpdatedAt());
                map().setRole(source.getUser().getRole());
            }
        };
    }

    private PropertyMap<Recruiter, RecruiterDto> recruiterMap() {
        return new PropertyMap<>() {
            @Override
            protected void configure() {
                map().setUsername(source.getUser().getUsername());
                map().setEmail(source.getUser().getEmail());
                map().setCreatedAt(source.getUser().getCreatedAt());
                map().setUpdatedAt(source.getUser().getUpdatedAt());
                map().setRole(source.getUser().getRole());
            }
        };
    }
}

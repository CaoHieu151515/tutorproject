package com.tutor.auth0r.service.impl;

import com.tutor.auth0r.domain.AppUser;
import com.tutor.auth0r.domain.Report;
import com.tutor.auth0r.domain.Tutor;
import com.tutor.auth0r.repository.AppUserRepository;
import com.tutor.auth0r.repository.ReportRepository;
import com.tutor.auth0r.repository.TutorRepository;
import com.tutor.auth0r.service.AppUserService;
import com.tutor.auth0r.service.ReportService;
import com.tutor.auth0r.service.dto.ReportDTO;
import com.tutor.auth0r.service.mapper.ReportMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.tutor.auth0r.domain.Report}.
 */
@Service
@Transactional
public class ReportServiceImpl implements ReportService {

    private static final Logger log = LoggerFactory.getLogger(ReportServiceImpl.class);

    private final ReportRepository reportRepository;

    private final ReportMapper reportMapper;

    private final AppUserService appUserService;

    private final TutorRepository tutorRepository;

    private final AppUserRepository appUserRepository;

    public ReportServiceImpl(
        ReportRepository reportRepository,
        ReportMapper reportMapper,
        AppUserService appUserService,
        TutorRepository tutorRepository,
        AppUserRepository appUserRepository
    ) {
        this.reportRepository = reportRepository;
        this.reportMapper = reportMapper;
        this.appUserService = appUserService;
        this.tutorRepository = tutorRepository;
        this.appUserRepository = appUserRepository;
    }

    @Override
    public ReportDTO save(ReportDTO reportDTO) {
        log.debug("Request to save Report : {}", reportDTO);
        Report report = reportMapper.toEntity(reportDTO);

        AppUser appuser = appUserRepository
            .findById(reportDTO.getAppUser().getId())
            .orElseThrow(() -> new RuntimeException("AppUser not found"));

        Tutor tutor = tutorRepository.findById(reportDTO.getTutor().getId()).orElseThrow(() -> new RuntimeException("Tutor not found"));

        report.setAppUser(appuser);
        report.setTutor(tutor);

        report = reportRepository.save(report);
        return reportMapper.toDto(report);
    }

    @Override
    public ReportDTO update(ReportDTO reportDTO) {
        log.debug("Request to update Report : {}", reportDTO);
        Report report = reportMapper.toEntity(reportDTO);
        report = reportRepository.save(report);
        return reportMapper.toDto(report);
    }

    @Override
    public Optional<ReportDTO> partialUpdate(ReportDTO reportDTO) {
        log.debug("Request to partially update Report : {}", reportDTO);

        return reportRepository
            .findById(reportDTO.getId())
            .map(existingReport -> {
                reportMapper.partialUpdate(existingReport, reportDTO);

                return existingReport;
            })
            .map(reportRepository::save)
            .map(reportMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReportDTO> findAll() {
        log.debug("Request to get all Reports");
        return reportRepository.findAll().stream().map(reportMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ReportDTO> findOne(Long id) {
        log.debug("Request to get Report : {}", id);
        return reportRepository.findById(id).map(reportMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Report : {}", id);
        reportRepository.deleteById(id);
    }
}

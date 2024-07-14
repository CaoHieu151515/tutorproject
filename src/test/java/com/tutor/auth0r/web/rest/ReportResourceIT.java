package com.tutor.auth0r.web.rest;

import static com.tutor.auth0r.domain.ReportAsserts.*;
import static com.tutor.auth0r.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutor.auth0r.IntegrationTest;
import com.tutor.auth0r.domain.Report;
import com.tutor.auth0r.repository.ReportRepository;
import com.tutor.auth0r.service.dto.ReportDTO;
import com.tutor.auth0r.service.mapper.ReportMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ReportResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ReportResourceIT {
    // private static final String DEFAULT_REPORT_DETAILS = "AAAAAAAAAA";
    // private static final String UPDATED_REPORT_DETAILS = "BBBBBBBBBB";

    // private static final LocalDate DEFAULT_TIME = LocalDate.ofEpochDay(0L);
    // private static final LocalDate UPDATED_TIME = LocalDate.now(ZoneId.systemDefault());

    // private static final String ENTITY_API_URL = "/api/reports";
    // private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    // private static Random random = new Random();
    // private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    // @Autowired
    // private ObjectMapper om;

    // @Autowired
    // private ReportRepository reportRepository;

    // @Autowired
    // private ReportMapper reportMapper;

    // @Autowired
    // private EntityManager em;

    // @Autowired
    // private MockMvc restReportMockMvc;

    // private Report report;

    // private Report insertedReport;

    // /**
    //  * Create an entity for this test.
    //  *
    //  * This is a static method, as tests for other entities might also need it,
    //  * if they test an entity which requires the current entity.
    //  */
    // public static Report createEntity(EntityManager em) {
    //     Report report = new Report().reportDetails(DEFAULT_REPORT_DETAILS).time(DEFAULT_TIME);
    //     return report;
    // }

    // /**
    //  * Create an updated entity for this test.
    //  *
    //  * This is a static method, as tests for other entities might also need it,
    //  * if they test an entity which requires the current entity.
    //  */
    // public static Report createUpdatedEntity(EntityManager em) {
    //     Report report = new Report().reportDetails(UPDATED_REPORT_DETAILS).time(UPDATED_TIME);
    //     return report;
    // }

    // @BeforeEach
    // public void initTest() {
    //     report = createEntity(em);
    // }

    // @AfterEach
    // public void cleanup() {
    //     if (insertedReport != null) {
    //         reportRepository.delete(insertedReport);
    //         insertedReport = null;
    //     }
    // }

    // @Test
    // @Transactional
    // void createReport() throws Exception {
    //     long databaseSizeBeforeCreate = getRepositoryCount();
    //     // Create the Report
    //     ReportDTO reportDTO = reportMapper.toDto(report);
    //     var returnedReportDTO = om.readValue(
    //         restReportMockMvc
    //             .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(reportDTO)))
    //             .andExpect(status().isCreated())
    //             .andReturn()
    //             .getResponse()
    //             .getContentAsString(),
    //         ReportDTO.class
    //     );

    //     // Validate the Report in the database
    //     assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
    //     var returnedReport = reportMapper.toEntity(returnedReportDTO);
    //     assertReportUpdatableFieldsEquals(returnedReport, getPersistedReport(returnedReport));

    //     insertedReport = returnedReport;
    // }

    // @Test
    // @Transactional
    // void createReportWithExistingId() throws Exception {
    //     // Create the Report with an existing ID
    //     report.setId(1L);
    //     ReportDTO reportDTO = reportMapper.toDto(report);

    //     long databaseSizeBeforeCreate = getRepositoryCount();

    //     // An entity with an existing ID cannot be created, so this API call must fail
    //     restReportMockMvc
    //         .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(reportDTO)))
    //         .andExpect(status().isBadRequest());

    //     // Validate the Report in the database
    //     assertSameRepositoryCount(databaseSizeBeforeCreate);
    // }

    // @Test
    // @Transactional
    // void getAllReports() throws Exception {
    //     // Initialize the database
    //     insertedReport = reportRepository.saveAndFlush(report);

    //     // Get all the reportList
    //     restReportMockMvc
    //         .perform(get(ENTITY_API_URL + "?sort=id,desc"))
    //         .andExpect(status().isOk())
    //         .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
    //         .andExpect(jsonPath("$.[*].id").value(hasItem(report.getId().intValue())))
    //         .andExpect(jsonPath("$.[*].reportDetails").value(hasItem(DEFAULT_REPORT_DETAILS)))
    //         .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME.toString())));
    // }

    // @Test
    // @Transactional
    // void getReport() throws Exception {
    //     // Initialize the database
    //     insertedReport = reportRepository.saveAndFlush(report);

    //     // Get the report
    //     restReportMockMvc
    //         .perform(get(ENTITY_API_URL_ID, report.getId()))
    //         .andExpect(status().isOk())
    //         .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
    //         .andExpect(jsonPath("$.id").value(report.getId().intValue()))
    //         .andExpect(jsonPath("$.reportDetails").value(DEFAULT_REPORT_DETAILS))
    //         .andExpect(jsonPath("$.time").value(DEFAULT_TIME.toString()));
    // }

    // @Test
    // @Transactional
    // void getNonExistingReport() throws Exception {
    //     // Get the report
    //     restReportMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    // }

    // @Test
    // @Transactional
    // void putExistingReport() throws Exception {
    //     // Initialize the database
    //     insertedReport = reportRepository.saveAndFlush(report);

    //     long databaseSizeBeforeUpdate = getRepositoryCount();

    //     // Update the report
    //     Report updatedReport = reportRepository.findById(report.getId()).orElseThrow();
    //     // Disconnect from session so that the updates on updatedReport are not directly saved in db
    //     em.detach(updatedReport);
    //     updatedReport.reportDetails(UPDATED_REPORT_DETAILS).time(UPDATED_TIME);
    //     ReportDTO reportDTO = reportMapper.toDto(updatedReport);

    //     restReportMockMvc
    //         .perform(
    //             put(ENTITY_API_URL_ID, reportDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(reportDTO))
    //         )
    //         .andExpect(status().isOk());

    //     // Validate the Report in the database
    //     assertSameRepositoryCount(databaseSizeBeforeUpdate);
    //     assertPersistedReportToMatchAllProperties(updatedReport);
    // }

    // @Test
    // @Transactional
    // void putNonExistingReport() throws Exception {
    //     long databaseSizeBeforeUpdate = getRepositoryCount();
    //     report.setId(longCount.incrementAndGet());

    //     // Create the Report
    //     ReportDTO reportDTO = reportMapper.toDto(report);

    //     // If the entity doesn't have an ID, it will throw BadRequestAlertException
    //     restReportMockMvc
    //         .perform(
    //             put(ENTITY_API_URL_ID, reportDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(reportDTO))
    //         )
    //         .andExpect(status().isBadRequest());

    //     // Validate the Report in the database
    //     assertSameRepositoryCount(databaseSizeBeforeUpdate);
    // }

    // @Test
    // @Transactional
    // void putWithIdMismatchReport() throws Exception {
    //     long databaseSizeBeforeUpdate = getRepositoryCount();
    //     report.setId(longCount.incrementAndGet());

    //     // Create the Report
    //     ReportDTO reportDTO = reportMapper.toDto(report);

    //     // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    //     restReportMockMvc
    //         .perform(
    //             put(ENTITY_API_URL_ID, longCount.incrementAndGet())
    //                 .contentType(MediaType.APPLICATION_JSON)
    //                 .content(om.writeValueAsBytes(reportDTO))
    //         )
    //         .andExpect(status().isBadRequest());

    //     // Validate the Report in the database
    //     assertSameRepositoryCount(databaseSizeBeforeUpdate);
    // }

    // @Test
    // @Transactional
    // void putWithMissingIdPathParamReport() throws Exception {
    //     long databaseSizeBeforeUpdate = getRepositoryCount();
    //     report.setId(longCount.incrementAndGet());

    //     // Create the Report
    //     ReportDTO reportDTO = reportMapper.toDto(report);

    //     // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    //     restReportMockMvc
    //         .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(reportDTO)))
    //         .andExpect(status().isMethodNotAllowed());

    //     // Validate the Report in the database
    //     assertSameRepositoryCount(databaseSizeBeforeUpdate);
    // }

    // @Test
    // @Transactional
    // void partialUpdateReportWithPatch() throws Exception {
    //     // Initialize the database
    //     insertedReport = reportRepository.saveAndFlush(report);

    //     long databaseSizeBeforeUpdate = getRepositoryCount();

    //     // Update the report using partial update
    //     Report partialUpdatedReport = new Report();
    //     partialUpdatedReport.setId(report.getId());

    //     partialUpdatedReport.reportDetails(UPDATED_REPORT_DETAILS).time(UPDATED_TIME);

    //     restReportMockMvc
    //         .perform(
    //             patch(ENTITY_API_URL_ID, partialUpdatedReport.getId())
    //                 .contentType("application/merge-patch+json")
    //                 .content(om.writeValueAsBytes(partialUpdatedReport))
    //         )
    //         .andExpect(status().isOk());

    //     // Validate the Report in the database

    //     assertSameRepositoryCount(databaseSizeBeforeUpdate);
    //     assertReportUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedReport, report), getPersistedReport(report));
    // }

    // @Test
    // @Transactional
    // void fullUpdateReportWithPatch() throws Exception {
    //     // Initialize the database
    //     insertedReport = reportRepository.saveAndFlush(report);

    //     long databaseSizeBeforeUpdate = getRepositoryCount();

    //     // Update the report using partial update
    //     Report partialUpdatedReport = new Report();
    //     partialUpdatedReport.setId(report.getId());

    //     partialUpdatedReport.reportDetails(UPDATED_REPORT_DETAILS).time(UPDATED_TIME);

    //     restReportMockMvc
    //         .perform(
    //             patch(ENTITY_API_URL_ID, partialUpdatedReport.getId())
    //                 .contentType("application/merge-patch+json")
    //                 .content(om.writeValueAsBytes(partialUpdatedReport))
    //         )
    //         .andExpect(status().isOk());

    //     // Validate the Report in the database

    //     assertSameRepositoryCount(databaseSizeBeforeUpdate);
    //     assertReportUpdatableFieldsEquals(partialUpdatedReport, getPersistedReport(partialUpdatedReport));
    // }

    // @Test
    // @Transactional
    // void patchNonExistingReport() throws Exception {
    //     long databaseSizeBeforeUpdate = getRepositoryCount();
    //     report.setId(longCount.incrementAndGet());

    //     // Create the Report
    //     ReportDTO reportDTO = reportMapper.toDto(report);

    //     // If the entity doesn't have an ID, it will throw BadRequestAlertException
    //     restReportMockMvc
    //         .perform(
    //             patch(ENTITY_API_URL_ID, reportDTO.getId())
    //                 .contentType("application/merge-patch+json")
    //                 .content(om.writeValueAsBytes(reportDTO))
    //         )
    //         .andExpect(status().isBadRequest());

    //     // Validate the Report in the database
    //     assertSameRepositoryCount(databaseSizeBeforeUpdate);
    // }

    // @Test
    // @Transactional
    // void patchWithIdMismatchReport() throws Exception {
    //     long databaseSizeBeforeUpdate = getRepositoryCount();
    //     report.setId(longCount.incrementAndGet());

    //     // Create the Report
    //     ReportDTO reportDTO = reportMapper.toDto(report);

    //     // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    //     restReportMockMvc
    //         .perform(
    //             patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
    //                 .contentType("application/merge-patch+json")
    //                 .content(om.writeValueAsBytes(reportDTO))
    //         )
    //         .andExpect(status().isBadRequest());

    //     // Validate the Report in the database
    //     assertSameRepositoryCount(databaseSizeBeforeUpdate);
    // }

    // @Test
    // @Transactional
    // void patchWithMissingIdPathParamReport() throws Exception {
    //     long databaseSizeBeforeUpdate = getRepositoryCount();
    //     report.setId(longCount.incrementAndGet());

    //     // Create the Report
    //     ReportDTO reportDTO = reportMapper.toDto(report);

    //     // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    //     restReportMockMvc
    //         .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(reportDTO)))
    //         .andExpect(status().isMethodNotAllowed());

    //     // Validate the Report in the database
    //     assertSameRepositoryCount(databaseSizeBeforeUpdate);
    // }

    // @Test
    // @Transactional
    // void deleteReport() throws Exception {
    //     // Initialize the database
    //     insertedReport = reportRepository.saveAndFlush(report);

    //     long databaseSizeBeforeDelete = getRepositoryCount();

    //     // Delete the report
    //     restReportMockMvc
    //         .perform(delete(ENTITY_API_URL_ID, report.getId()).accept(MediaType.APPLICATION_JSON))
    //         .andExpect(status().isNoContent());

    //     // Validate the database contains one less item
    //     assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    // }

    // protected long getRepositoryCount() {
    //     return reportRepository.count();
    // }

    // protected void assertIncrementedRepositoryCount(long countBefore) {
    //     assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    // }

    // protected void assertDecrementedRepositoryCount(long countBefore) {
    //     assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    // }

    // protected void assertSameRepositoryCount(long countBefore) {
    //     assertThat(countBefore).isEqualTo(getRepositoryCount());
    // }

    // protected Report getPersistedReport(Report report) {
    //     return reportRepository.findById(report.getId()).orElseThrow();
    // }

    // protected void assertPersistedReportToMatchAllProperties(Report expectedReport) {
    //     assertReportAllPropertiesEquals(expectedReport, getPersistedReport(expectedReport));
    // }

    // protected void assertPersistedReportToMatchUpdatableProperties(Report expectedReport) {
    //     assertReportAllUpdatablePropertiesEquals(expectedReport, getPersistedReport(expectedReport));
    // }
}

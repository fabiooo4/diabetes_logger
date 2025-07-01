package com.univr.diabetes_logger.service;

import java.util.Optional;

import com.univr.diabetes_logger.model.Patient;
import com.univr.diabetes_logger.model.Report;
import com.univr.diabetes_logger.model.Therapy;
import com.univr.diabetes_logger.repository.ReportRepository;
import org.springframework.stereotype.Service;

import com.univr.diabetes_logger.model.Medic;
import com.univr.diabetes_logger.repository.MedicRepository;

/**
 * MedicServiceImpl
 */
@Service
public class ReportService implements CrudService<Report> {
    private ReportRepository repository;

    public ReportService(ReportRepository repository) {
        this.repository = repository;
    }

    @Override
    public Report create(Report report) {
        return repository.save(report);
    }

    @Override
    public Iterable<Report> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Report> getById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Report update(Integer id, Report report) {
        Report existingReport = this.getById(id).orElseThrow();

        existingReport.setGlycemiaLevel(report.getGlycemiaLevel());
        existingReport.setBeforeMeal(report.getBeforeMeal());
        existingReport.setSymptoms(report.getSymptoms());
        existingReport.setNotes(report.getNotes());
        existingReport.setDay(report.getDay());
        existingReport.setTime(report.getTime());
        existingReport.setMedicine(report.getMedicine());
        existingReport.setAmount(report.getAmount());
        existingReport.setPatient(report.getPatient());

        return repository.save(existingReport);
    }

    @Override
    public Report delete(Integer id) {
        Report deleted = this.getById(id).orElseThrow();

        repository.deleteById(id);

        return deleted;
    }
}

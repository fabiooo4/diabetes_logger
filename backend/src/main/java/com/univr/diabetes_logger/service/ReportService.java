package com.univr.diabetes_logger.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.univr.diabetes_logger.model.Patient;
import com.univr.diabetes_logger.model.Report;
import com.univr.diabetes_logger.model.User;
import com.univr.diabetes_logger.repository.ReportRepository;
import com.univr.diabetes_logger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * MedicServiceImpl
 */
@Service
public class ReportService implements CrudService<Report> {
    private ReportRepository repository;
    @Autowired
    private UserRepository userRepository;

    public ReportService(ReportRepository repository) {
        this.repository = repository;
    }

    @Override
    public Report create(Report report) {
        // TODO: Check if data are coherent with therapy

        return repository.save(report);
    }

    public Optional<Report> createOnUser(Report report, Integer id) {
        Optional<User> user = userRepository.findById(id);

        if(user.isEmpty()) {
            return Optional.empty();
        }

        if(user.get().getPatient() == null){
            return Optional.empty();
        }

        report.setPatient(user.get().getPatient());
        return Optional.of(repository.save(report));
    }

    @Override
    public Iterable<Report> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Report> getById(Integer id) {
        return repository.findById(id);
    }

    public List<Report> getAllByUserId(Integer userId) {

       List<Report> listOfReports = repository.findAll();

       return listOfReports.stream().filter(report -> {

           Patient patient = report.getPatient();
           if(patient == null) {
               return false;
           }

           User user = patient.getUser();
           if(user == null) {
               return false;
           }

           return user.getId().equals(userId);
       }

       ).collect(Collectors.toList());
    }

    @Override
    public Report update(Integer id, Report report) {
        Report existingReport = this.getById(id).orElseThrow();

        // TODO: Modify the update
        existingReport.setGlycemiaLevel(report.getGlycemiaLevel());
        existingReport.setBeforeMeal(report.getBeforeMeal());
        existingReport.setSymptoms(report.getSymptoms());
        existingReport.setNotes(report.getNotes());
        existingReport.setDateTime(report.getDateTime());
        existingReport.setMedicine(report.getMedicine());
        existingReport.setAmount(report.getAmount());
        existingReport.setPatient(report.getPatient());

        return repository.save(existingReport);
    }

    public Report updateOnUser(Integer id, Report report) {
        Report existingReport = this.getById(id).orElseThrow();

        // TODO: Modify the update
        existingReport.setGlycemiaLevel(report.getGlycemiaLevel());
        existingReport.setBeforeMeal(report.getBeforeMeal());
        existingReport.setSymptoms(report.getSymptoms());
        existingReport.setNotes(report.getNotes());
        existingReport.setDateTime(report.getDateTime());
        existingReport.setMedicine(report.getMedicine());
        existingReport.setAmount(report.getAmount());
        // Cannot change patient

        return repository.save(existingReport);
    }

    @Override
    public Report delete(Integer id) {
        Report deleted = this.getById(id).orElseThrow();

        repository.deleteById(id);

        return deleted;
    }
}

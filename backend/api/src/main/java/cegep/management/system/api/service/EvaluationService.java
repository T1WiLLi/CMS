package cegep.management.system.api.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cegep.management.system.api.error.ResourceNotFoundException;
import cegep.management.system.api.model.Evaluation;
import cegep.management.system.api.repo.EvaluationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EvaluationService {

    private final EvaluationRepository evaluationRepository;

    public EvaluationService(EvaluationRepository evaluationRepository) {
        this.evaluationRepository = evaluationRepository;
    }

    public List<Evaluation> getAllEvaluations() {
        return evaluationRepository.findAll();
    }

    public Optional<Evaluation> getEvaluationById(Long evaluationId) {
        return evaluationRepository.findById(evaluationId);
    }

    public Evaluation createEvaluation(Evaluation evaluation) {
        return evaluationRepository.save(evaluation);
    }

    @Transactional
    public Evaluation updateEvaluation(Long evaluationId, Evaluation updatedEvaluation) {
        return evaluationRepository.findById(evaluationId).map(evaluation -> {
            evaluation.setName(updatedEvaluation.getName());
            evaluation.setCourse(updatedEvaluation.getCourse());
            evaluation.setPonderation(updatedEvaluation.getPonderation());
            evaluation.setDenominator(updatedEvaluation.getDenominator());
            return evaluationRepository.save(evaluation);
        }).orElseThrow(() -> new ResourceNotFoundException("Evaluation not found"));
    }

    public void deleteEvaluation(Long evaluationId) {
        evaluationRepository.deleteById(evaluationId);
    }
}

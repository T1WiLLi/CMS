package cegep.management.system.api.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cegep.management.system.api.error.ResourceNotFoundException;
import cegep.management.system.api.model.Program;
import cegep.management.system.api.repo.ProgramRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProgramService {

    private final ProgramRepository programRepository;

    public ProgramService(ProgramRepository programRepository) {
        this.programRepository = programRepository;
    }

    public List<Program> getAllPrograms() {
        return programRepository.findAll();
    }

    public Optional<Program> getProgramById(Long programId) {
        return programRepository.findById(programId);
    }

    public Program createProgram(Program program) {
        return programRepository.save(program);
    }

    @Transactional
    public Program updateProgram(Long programId, Program updatedProgram) {
        return programRepository.findById(programId).map(program -> {
            program.setName(updatedProgram.getName());
            program.setDepartment(updatedProgram.getDepartment());
            return programRepository.save(program);
        }).orElseThrow(() -> new ResourceNotFoundException("Program not found"));
    }

    public void deleteProgram(Long programId) {
        programRepository.deleteById(programId);
    }
}

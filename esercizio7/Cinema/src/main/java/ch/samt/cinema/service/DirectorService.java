package ch.samt.cinema.service;

import ch.samt.cinema.data.DirectorRepository;
import ch.samt.cinema.domain.Director;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DirectorService {
    private final DirectorRepository directorRepository;

    @Autowired
    public DirectorService(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    public List<Director> findAll(){
        return directorRepository.findAll();
    }

    public Director findById(long id){
        return directorRepository.findById(id);
    }

    public Director save(Director director){
        return directorRepository.save(director);
    }
}

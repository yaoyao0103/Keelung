package mongoDB.service;

import mongoDB.entity.Sight;
import mongoDB.parameter.SightQueryParameter;
import mongoDB.repository.SightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SightService {

    @Autowired
    private SightRepository repository;

    public Sight createSight(Sight request) {
        Sight sight = new Sight();
        sight.setSightName(request.getSightName());
        sight.setZone(request.getZone());
        sight.setCategory(request.getCategory());
        sight.setPhotoURL(request.getPhotoURL());
        sight.setDescription(request.getDescription());
        sight.setAddress(request.getAddress());

        return repository.insert(sight);
    }

    public List<Sight> getSights(SightQueryParameter param){
        return repository.findByZoneLike(param.getZone()).get();
    }

    public void deleteSight() {
        repository.deleteAll();
    }

}

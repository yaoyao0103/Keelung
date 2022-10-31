package attraction.HW2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Service
public class SightService {
    @Autowired
    private SightDAO sightDAO;

    public List<Sight> getSights(SightQueryParameter param){
        return sightDAO.find(param);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public class NotFoundException extends RuntimeException {

        public NotFoundException(String message) {
            super(message);
        }
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public class UnprocessableEntityException extends RuntimeException {

        public UnprocessableEntityException(String message) {
            super(message);
        }

    }
}

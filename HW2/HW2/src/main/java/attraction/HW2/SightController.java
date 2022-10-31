package attraction.HW2;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/SightAPI", produces = MediaType.APPLICATION_JSON_VALUE)


public class SightController {


    @Autowired
    private SightService sightService;

    @GetMapping
    public ResponseEntity<List<Sight>> getProducts(@ModelAttribute SightQueryParameter param) {
        List<Sight> sights = sightService.getSights(param);
        return ResponseEntity.ok(sights);
    }
}

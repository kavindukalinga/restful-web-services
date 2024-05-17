package com.kalinga.rest.webservices.restfulwebservices.termaccess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/mages")
public class MagesController {

    @Autowired
    private MageRepository magesRepository;

    @Autowired
    private EncryptionService encryptionService;

    @PostMapping
    public Mages createMage(@RequestBody MagesRequest request) throws Exception {
        Mages mage = new Mages();
        mage.setName(request.getName());
        return magesRepository.save(mage);
    }

    @GetMapping("/{id}")
    public Mages getMage(@PathVariable Integer id) throws Exception {
        Optional<Mages> optionalMage = magesRepository.findById(id);
        if (optionalMage.isPresent()) {
            return optionalMage.get();
        } else {
            throw new RuntimeException("Mage not found with id " + id);
        }
    }

    @GetMapping
    public List<Mages> getAllMages() {
        return magesRepository.findAll();
    }

    public static class MagesRequest {
        private String name;

        // Getters and setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}

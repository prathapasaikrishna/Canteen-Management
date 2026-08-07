package com.canteen.management.controller;

import com.canteen.management.entity.Banner;
import com.canteen.management.repository.BannerRepository;
import com.canteen.management.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/banner")
@RequiredArgsConstructor
@CrossOrigin("*")
public class BannerController {

    private final BannerRepository bannerRepository;
    private final CloudinaryService cloudinaryService;

    @GetMapping("/all")
    public List<Banner> getAllBanners() {
        return bannerRepository.findAll();
    }

    @PostMapping("/add")
    public ResponseEntity<Banner> addBanner(
            @RequestParam("image") MultipartFile image,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description", required = false) String description) {

        String imageUrl = cloudinaryService.uploadImage(image);
        if (imageUrl == null || imageUrl.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Banner banner = new Banner();
        banner.setImageUrl(imageUrl);
        banner.setTitle(title);
        banner.setDescription(description);

        Banner saved = bannerRepository.save(banner);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBanner(@PathVariable Long id) {
        if (!bannerRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        bannerRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}

package world.ucode.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import world.ucode.domain.Pictures;
import world.ucode.domain.Profile;
import world.ucode.domain.Registration;
import world.ucode.domain.Tag;
import world.ucode.repos.PictureRepo;
import world.ucode.repos.ProfileRepo;
import world.ucode.repos.TagRepo;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;

@Controller
public class MainpageController {

    @Autowired
    private ProfileRepo profileRepo;

    @Autowired
    private TagRepo tagRepo;

    @Autowired
    private PictureRepo pictureRepo;

    @Value("/Users/olopushans")
    private String uploadPath;

    @GetMapping("/mainpage")
    public String show(@AuthenticationPrincipal Registration username, Model model) {
        Profile profile = profileRepo.findByUsername(username);
        if (profile != null) {
            Date data = new Date();
            Date birth = null;
            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
            try {
                birth = dateFormatter.parse(profile.getBirthDate());
            } catch (Exception e) {
                e.printStackTrace();
            }
            Period period = Period.between(data.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                    birth.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            int a = period.getYears();
            if (a < 0)
                a = a * (-1);
            profile.setBirthDate(String.valueOf(a));
            model.addAttribute("tag", profile.getFollowedTag());
        } else
            model.addAttribute("profile", null);
        model.addAttribute("profile", profile);
        return "mainpage";
    }

    @GetMapping("/mainpage/mypicture")
    public String showGallery(@AuthenticationPrincipal Registration registration, Model model) {
        Profile profile = profileRepo.findByUsername(registration);
        List<Pictures> pictures = pictureRepo.findByProfile(profile);
        model.addAttribute("mypictures", pictures);
        return "mainpage";
    }

    @PostMapping("/mainpage")
    public String doit(@AuthenticationPrincipal Registration username, Model model) {
        Profile profile = profileRepo.findByUsername(username);
        if (profile != null) {
            Date data = new Date();
            Date birth = null;
            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
            try {
                birth = dateFormatter.parse(profile.getBirthDate());
            } catch (Exception e) {
                e.printStackTrace();
            }
            Period period = Period.between(data.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                    birth.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            int a = period.getYears();
            if (a < 0)
                a = a * (-1);
            profile.setBirthDate(String.valueOf(a));
            model.addAttribute("tag", profile.getFollowedTag());
        } else
            model.addAttribute("profile", null);
        model.addAttribute("profile", profile);
        return "mainpage";
    }

    @PostMapping("/mainpage/search")
    public String rearch(@RequestParam("parameter") String param, @RequestParam String search, Model model) {
        List<Profile> prof = new ArrayList<>();
        if (param.equals("firstname") == true) {
            prof = profileRepo.findByFirstname(search);
        } else if (param.equals("surname") == true) {
            prof = profileRepo.findBySurname(search);
        } else if (param.equals("city") == true) {
            prof = profileRepo.findByCity(search);
        } else if (param.equals("workPlace") == true) {
            prof = profileRepo.findAByWorkPlace(search);
        } else if (param.equals("position") == true) {
            prof = profileRepo.findByPosition(search);
        } else if (param.equals("tag") == true) {
            Tag tag = tagRepo.findByTagName(search);
            prof = tag.getFollowers();
        }
        model.addAttribute("profiles", prof);
        return "mainpage";
    }

    @PostMapping("/mainpage/like")
    public String likeSomeone(@AuthenticationPrincipal Registration username,
                              @RequestParam String photoName, Model model) {
        Profile profile = profileRepo.findByUsername(username);
        Profile profile2 = profileRepo.findByPhotoName(photoName);
        Registration registration = profile2.getUsername();
        profile.setLikers(username);
        profile.setIlike(registration);
        if(profile.getPhotoName().equals(photoName) != true)
            profileRepo.save(profile);
        return "redirect:/mainpage";
    }

    @PostMapping("/mainpage/list/like")
    public String getLikers(@AuthenticationPrincipal Registration username, Model model) {
        Profile profile = profileRepo.findByUsername(username);
        Set<Registration> lik = new HashSet<>();
        Set<Profile> likers = new HashSet<>();
        lik =  profile.getLikers();
        for(Registration r : lik) {
            Profile pr = profileRepo.findByUsername(r);
            likers.add(pr);
        }
        model.addAttribute("likers", likers);
        return "mainpage";
    }

    @PostMapping("/mainpage/list/Ili")
    public String getIlike(@AuthenticationPrincipal Registration username, Model model) {
        Profile profile = profileRepo.findByUsername(username);
        Set<Registration> Ilike = new HashSet<>();
        Set<Profile> Ili = new HashSet<>();
        Ilike =  profile.getIlike() ;
        for(Registration r : Ilike) {
            Profile pr = profileRepo.findByUsername(r);
            Ili.add(pr);
        }
        model.addAttribute("ILI", Ili);
        return "mainpage";
    }

    @PostMapping("/mainpage/makePhoto")
    public String makePhoto(@AuthenticationPrincipal Registration registration, @RequestParam("picture") MultipartFile file,
                            Model model)  throws IOException {
        Profile profile = profileRepo.findByUsername(registration);
        if (profile != null) {
            if (file != null && !file.getOriginalFilename().isEmpty()) {
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }
                String uuidFile = UUID.randomUUID().toString();
                String resultFilename = uuidFile + "." + file.getOriginalFilename();
                file.transferTo(new File(uploadPath + "/" + resultFilename));
                Pictures pic = new Pictures(profile, resultFilename);
                pictureRepo.save(pic);
                model.addAttribute("picture", "Вы загрузили фото");
            } else
                model.addAttribute("profile", "Не получилось загрузить фото");
        }
        if(profile != null) {
            Date data = new Date();
            Date birth = null;
            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
            try {
                birth = dateFormatter.parse(profile.getBirthDate());
            } catch (Exception e) {
                e.printStackTrace();
            }
            Period period = Period.between(data.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                    birth.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            int a = period.getYears();
            if(a < 0)
                a = a * (-1);
            profile.setBirthDate(String.valueOf(a));
            model.addAttribute("tag", profile.getFollowedTag());
        } else
            model.addAttribute("profile", null);
        model.addAttribute("profile", profile);
        return "mainpage";
    }
}


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
import world.ucode.domain.Profile;
import world.ucode.domain.Registration;
import world.ucode.domain.Tag;
import world.ucode.repos.ProfileRepo;
import world.ucode.repos.TagRepo;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class ProfileController {

    @Value("/Users/olopushans")
    private String uploadPath;

    @Autowired
    private ProfileRepo profileRepo;

    @Autowired
    private TagRepo tagRepo;

    @GetMapping("/profile")
    public String make() {
        return "profile";
    }

    @PostMapping("/profile")
    public String makeProfile(@AuthenticationPrincipal Registration username, @RequestParam String firstname,
                              @RequestParam String surname, @RequestParam String birthDate,
                              @RequestParam("sex") String sex, @RequestParam String city,
                              @RequestParam("file") MultipartFile file,
                              @RequestParam String workPlace, @RequestParam String position,
                              @RequestParam String bio, @RequestParam("tag") String[] tag, Model model) throws IOException {
        makeTag();
        if (firstname.equals("") != true) {
            if (surname.equals("") != true) {
                if (birthDate.equals("") != true) {
                    if (city.equals("") != true) {
                        if (workPlace.equals("") != true) {
                            if (position.equals("") != true) {
                                if (bio.equals("") != true) {
                                    Profile profile = profileRepo.findByUsername(username);
                                    if (profile == null) {
                                        if (file != null && !file.getOriginalFilename().isEmpty()) {
                                            File uploadDir = new File(uploadPath);
                                            if (!uploadDir.exists()) {
                                                uploadDir.mkdir();
                                            }
                                            String uuidFile = UUID.randomUUID().toString();
                                            String resultFilename = uuidFile + "." + file.getOriginalFilename();
                                            file.transferTo(new File(uploadPath + "/" + resultFilename));
                                            profile = new Profile(username, firstname, surname, birthDate, sex,
                                                    city, resultFilename, workPlace, position, bio);
                                            profileRepo.save(profile);
                                            for (int a = 0; a < tag.length; a++) {
                                                Tag t = tagRepo.findByTagName(tag[a]);
                                                t.addProfile(profile);
                                                profile.followTag(t);
                                            }
                                            profileRepo.save(profile);
                                            model.addAttribute("profile", "Вы создали профиль");
                                        } else
                                            model.addAttribute("profile", "Не получилось загрузить фото");
                                    } else
                                        model.addAttribute("profile", "Вы не можете создать профиль, у Вас уже есть один");
                                } else
                                    model.addAttribute("profile", "Поле опишите себя нужно заполнить");
                            } else
                                model.addAttribute("profile", "Поле занимаемая должность нужно заполнить");
                        } else
                            model.addAttribute("profile", "Поле место работы нужно заполнить");
                    } else
                        model.addAttribute("profile", "Поле город проживания нужно заполнить");
                } else
                    model.addAttribute("profile", "Поле дата рождения нужно заполнить");
            } else
                model.addAttribute("profile", "Поле фамилия нужно заполнить");
        } else
            model.addAttribute("profile", "Поле имя нужно заполнить");
        return "profile";
    }

    public void makeTag() {
        String[] t = new String[]{"C", "C++", "Java", "Python", "JavaScript", "Frontend", "Backend", "Iot", "Fullstack",
                "Devops", "Gamedev", "ищу друга", "ищу работу", "ищу работника"};
        for( int i = 0; i < 14; i++) {
            Tag ta = tagRepo.findByTagName(t[i]);
            if(ta == null) {
                Tag tag = new Tag(t[i]);
                tagRepo.save(tag);
            }
        }
    }
}


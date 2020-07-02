package AplikacjeInternetowe.ZarazeniaKorona.controllers;

import AplikacjeInternetowe.ZarazeniaKorona.servies.KoronaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @Autowired
    KoronaService koronaService;
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("StatystykiLokalizacji", koronaService.getAllStats());
        return "home";
    }
}

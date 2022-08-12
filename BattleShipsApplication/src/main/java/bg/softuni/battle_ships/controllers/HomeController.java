package bg.softuni.battle_ships.controllers;


import bg.softuni.battle_ships.models.DTO.ShipDTO;

import bg.softuni.battle_ships.models.DTO.StartBattleDTO;
import bg.softuni.battle_ships.services.AuthService;
import bg.softuni.battle_ships.services.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HomeController {


    private final AuthService authService;
    private final ShipService shipService;

    @Autowired
    public HomeController(AuthService authService, ShipService shipService) {
        this.authService = authService;
        this.shipService = shipService;
    }
    @ModelAttribute("startBattleDTO")
    public StartBattleDTO initstartBattleDTO(){
        return new StartBattleDTO();
    }

    @GetMapping("/")
    public String loggedOutIndex() {
        if (this.authService.isLoggedIn()) {
            return "redirect:/home";
        }

        return "index";
    }

    @GetMapping("/home")
    public String loggedInIndex(Model model) {
        if (!this.authService.isLoggedIn()) {
            return "redirect:/";
        }
        long loggedUserId = this.authService.getLoggedUserId();

        List<ShipDTO> ownShips = this.shipService.getShipsOwnedBy(loggedUserId);
        List<ShipDTO> enemyShips = this.shipService.getShipsNotOwnedBy(loggedUserId);
        List<ShipDTO> sortedShips = this.shipService.gelAllSorted();
        //
        model.addAttribute("ownShips", ownShips);
        model.addAttribute("enemyShips", enemyShips);
        model.addAttribute("sortedShips", sortedShips);

        return "home";
    }


}

package bg.softuni.battle_ships.services;

import bg.softuni.battle_ships.models.Category;
import bg.softuni.battle_ships.models.DTO.CreateShipDTO;
import bg.softuni.battle_ships.models.DTO.ShipDTO;
import bg.softuni.battle_ships.models.Ship;
import bg.softuni.battle_ships.models.ShipType;
import bg.softuni.battle_ships.models.User;
import bg.softuni.battle_ships.repositories.CategoryRepository;
import bg.softuni.battle_ships.repositories.ShipRepository;
import bg.softuni.battle_ships.repositories.UserRepository;
import bg.softuni.battle_ships.util.LoggedUser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShipService {

    private final ShipRepository shipRepository;
    private final CategoryRepository categoryRepository;
    private final LoggedUser loggedUser;
    private final UserRepository userRepository;

    public ShipService(ShipRepository shipRepository, CategoryRepository categoryRepository, LoggedUser loggedUser, UserRepository userRepository) {
        this.shipRepository = shipRepository;
        this.categoryRepository = categoryRepository;
        this.loggedUser = loggedUser;
        this.userRepository = userRepository;
    }


    public boolean create(CreateShipDTO createShipDTO) {

        Optional<Ship> byName =
                this.shipRepository.findByName(createShipDTO.getName());

        if(byName.isPresent()){
            return false;
        }

        ShipType type = switch (createShipDTO.getCategory()){
            case 0 -> ShipType.BATTLE;
            case 1 -> ShipType.CARGO;
            case 2 -> ShipType.PATROL;
            default -> ShipType.BATTLE;
        };

        Category category = this.categoryRepository.findByName(type);
        Optional<User> owner = this.userRepository.findById(this.loggedUser.getId());
        Ship ship = new Ship();
        ship.setName(createShipDTO.getName());
        ship.setPower(createShipDTO.getPower());
        ship.setHealth(createShipDTO.getHealth());
        ship.setCreated(createShipDTO.getCreated());
        ship.setCategory(category);
        ship.setUser(owner.get());

        this.shipRepository.save(ship);

        return true;
    }

    public List<ShipDTO> getShipsOwnedBy(long ownerId) {
        return this.shipRepository.findByUserId(ownerId)
                .stream()
                .map(ship -> new ShipDTO(ship))
                .collect(Collectors.toList());
    }


    public List<ShipDTO> getShipsNotOwnedBy(long ownerId) {
        return this.shipRepository.findByUserIdNot(ownerId)
                .stream()
                .map(ship -> new ShipDTO(ship))
                .collect(Collectors.toList());
    }

    public List<ShipDTO> gelAllSorted() {
        return this.shipRepository.findOrderByHealthAscNameDescPowerAsc()
                .stream()
                .map(ship -> new ShipDTO(ship))
                .collect(Collectors.toList());
    }
}

  

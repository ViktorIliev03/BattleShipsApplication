package bg.softuni.battle_ships.repositories;

import bg.softuni.battle_ships.models.Category;
import bg.softuni.battle_ships.models.ShipType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(ShipType type);
}

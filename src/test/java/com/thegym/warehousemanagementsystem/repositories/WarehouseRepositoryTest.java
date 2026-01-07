package com.thegym.warehousemanagementsystem.repositories;


import com.thegym.warehousemanagementsystem.entities.Warehouse;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class WarehouseRepositoryTest {
    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private EntityManager entityManager;

    private Warehouse warehouse;

    @BeforeEach
    public void setup() {
        warehouse = new Warehouse();
        warehouse.setName("Warehouse test");
        warehouse.setWarehouseNumber("120");
    }

    @Nested
    @DisplayName("Create warehouse tests")
    class CreateWarehouse {
        @Test
        @DisplayName("Should Create warehouse successful")
        public void warehouse_create_successful() {
            Warehouse savedWarehouse = warehouseRepository.saveAndFlush(warehouse);
            Assertions.assertNotNull(savedWarehouse);
        }

        @Test
        @DisplayName("Should generate ID when warehouse is saved")
        public void warehouse_create_id_generated() {
            Warehouse savedWarehouse = warehouseRepository.saveAndFlush(warehouse);
            Assertions.assertNotNull(savedWarehouse.getId());
        }

        @Test
        @DisplayName("Should save with default values")
        public void warehouse_create_with_default_values() {
            Warehouse savedWarehouse = warehouseRepository.saveAndFlush(warehouse);
            Assertions.assertEquals(0, savedWarehouse.getVersion());
            Assertions.assertEquals(true, savedWarehouse.getActive());
        }
    }

    @Nested
    @DisplayName("Update warehouse tests")
    class UpdateWarehouse {
        @Test
        @DisplayName("Should update warehouse successful and update version")
        public void warehouse_update_successful() {
            Warehouse savedWarehouse = warehouseRepository.saveAndFlush(warehouse);
            var initialVersion= savedWarehouse.getVersion();

            savedWarehouse.setName("Warehouse updated");
            savedWarehouse.setActive(false);
            var updatedWarehouse = warehouseRepository.saveAndFlush(savedWarehouse);

            Assertions.assertNotNull(updatedWarehouse);
            Assertions.assertEquals(initialVersion + 1, updatedWarehouse.getVersion());
            Assertions.assertFalse(updatedWarehouse.getActive());
        }

        @Test
        @DisplayName("Should throw Optimistic lock exception on concurrent updates")
        void warehouse_update_exception_on_concurrent_updates() {
            Warehouse saved = warehouseRepository.saveAndFlush(warehouse);

            Warehouse firstLoad = warehouseRepository.findById(saved.getId()).get();
            Warehouse secondLoad = warehouseRepository.findById(saved.getId()).get();

            entityManager.detach(firstLoad);


            firstLoad.setName("First update");
            warehouseRepository.saveAndFlush(firstLoad);

            secondLoad.setName("Second update");

            Assertions.assertThrows(ObjectOptimisticLockingFailureException.class, () -> warehouseRepository.saveAndFlush(secondLoad));
        }


    }

}

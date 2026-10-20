package org.example.shelter;

import org.example.model.AdoptionStatus;
import org.example.model.Animal;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Shelter<T extends Animal> {
    private final List<T> animals = new ArrayList<>();

    public void addAnimal(T animal) {
        animals.add(animal);
    }

    public List<T> getAllAnimals() {
        return List.copyOf(animals);
        //return null;
    }

    public List<T> findBySpecies(String species) {
        return animals.stream()
                .filter(a -> Objects.equals(a.getSpecies(), species))
                .toList();
    }

    public List<T> findAvailableAnimals() {
        return animals.stream()
                .filter(a -> a.getAdoptionStatus() == AdoptionStatus.AVAILABLE)
                .toList();
    }

    public void markAsAdopted(String id) {
        for (T animal : animals) {
            if (animal.getId().toString().equals(id)) {
                if (animal.getAdoptionStatus() == AdoptionStatus.ADOPTED) {
                    throw new IllegalStateException("Animal " + id + " is already adopted");
                }
                animal.markAsAdopted();
                return;
            }
        }
        throw new IllegalArgumentException("No animal with ID: " + id);
    }
}

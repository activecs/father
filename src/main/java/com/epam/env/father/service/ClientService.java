package com.epam.env.father.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.objects.User;

import com.epam.env.father.model.Client;
import com.epam.env.father.repository.ClientRepository;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public Client register(User user) {
        Client client = Client.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
        return clientRepository.insert(client);
    }

    public Client update(Client client) {
        return clientRepository.save(client);
    }

    public Optional<Client> findById(Integer id) {
        return Optional.ofNullable(clientRepository.findOne(id));
    }
}

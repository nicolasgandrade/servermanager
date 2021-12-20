package com.nicolasgandrade.servermanager.service.implementation;

import com.nicolasgandrade.servermanager.enums.Status;
import com.nicolasgandrade.servermanager.model.Server;
import com.nicolasgandrade.servermanager.repository.ServerRepository;
import com.nicolasgandrade.servermanager.service.ServerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Random;

import static com.nicolasgandrade.servermanager.enums.Status.SERVER_DOWN;
import static com.nicolasgandrade.servermanager.enums.Status.SERVER_UP;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ServerServiceImpl implements ServerService {

    private final ServerRepository repository;

    @Override
    public Server create(Server server) {
        log.info("Saving new server: {}", server.getName()); //Create log message
        server.setImgUrl(setServerImageUrl()); //Set the server image
        return repository.save(server); //Save the new server
    }

    @Override
    public Server ping(String ipAddress) throws IOException {
        log.info("Pinging server: {}", ipAddress);
        Server server = repository.findByIpAddress(ipAddress);
        InetAddress address = InetAddress.getByName(ipAddress);
        server.setStatus(address.isReachable(10000) ? SERVER_UP : SERVER_DOWN);
        repository.save(server);
        return server;
    }

    @Override
    public Collection<Server> list(int limit) {
        log.info("Fetching all servers");
        return repository.findAll(PageRequest.of(0, limit)).toList();
    }

    @Override
    public Server get(Long id) {
        log.info("Fetching server by id: {}", id);
        return repository.findById(id).get();
    }

    @Override
    public Server update(Server server) {
        log.info("Updating server: {}", server.getName());
        return repository.save(server);
    }

    @Override
    public Boolean delete(Long id) {
        log.info("Deleting server by id: {}", id);
        repository.deleteById(id);
        return Boolean.TRUE;
    }

    private String setServerImageUrl() {
        String[] imageNames = {"s1.png", "s2.png", "s3.png", "s4.png",};
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/servers/images/"
                + imageNames[new Random().nextInt(4)])
                .toUriString(); //localhost:8080/servers/images/sx.png
    }
}

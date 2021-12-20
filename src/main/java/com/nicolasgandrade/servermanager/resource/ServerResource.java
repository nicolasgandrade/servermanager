package com.nicolasgandrade.servermanager.resource;

import com.nicolasgandrade.servermanager.enums.Status;
import com.nicolasgandrade.servermanager.model.Response;
import com.nicolasgandrade.servermanager.model.Server;
import com.nicolasgandrade.servermanager.service.implementation.ServerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Map;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@RestController
@RequestMapping("/servers")
@RequiredArgsConstructor
public class ServerResource {
    private final ServerServiceImpl service;

    @GetMapping(path = "/list")
    public ResponseEntity<Response> getServers() {
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(now())
                        .data(Map.of("servers", service.list(30)))
                        .message("Servers retrieved")
                        .status(HttpStatus.OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping(path = "/ping/{ipAddress}")
    public ResponseEntity<Response> pingServer(@PathVariable String ipAddress) throws IOException {
        Server server = service.ping(ipAddress);
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(now())
                        .data(Map.of("server", server))
                        .message(server.getStatus() == Status.SERVER_UP ? "Ping success" : "Ping failed")
                        .status(HttpStatus.OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @PostMapping(path = "/save")
    //@Valid calls @NotEmpty message at Server entity
    public ResponseEntity<Response> saveServer(@RequestBody @Valid Server server) {
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(now())
                        .data(Map.of("server", service.create(server)))
                        .message("Server created")
                        .status(HttpStatus.CREATED)
                        .statusCode(CREATED.value())
                        .build()
        );
    }

    @GetMapping(path = "/get/{id}")
    public ResponseEntity<Response> getServer(@PathVariable Long id) {
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(now())
                        .data(Map.of("server", service.get(id)))
                        .message("Server retrieved")
                        .status(HttpStatus.OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Response> deleteServer(@PathVariable Long id) {
        return ResponseEntity.ok(
                Response.builder()
                        .timestamp(now())
                        .data(Map.of("deleted", service.delete(id)))
                        .message("Server deleted")
                        .status(HttpStatus.OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping(path = "/images/{name}", produces = IMAGE_PNG_VALUE)
    public byte[] getServerImage(@PathVariable String name) throws IOException {
        return Files.readAllBytes(Paths.get(System.getProperty("user.home") + "/OneDrive/Imagens/imgs/" + name));
    }
}

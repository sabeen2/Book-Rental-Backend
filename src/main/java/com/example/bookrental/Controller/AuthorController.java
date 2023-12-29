package com.example.bookrental.Controller;

import com.example.bookrental.Dto.AuthorDto;
import com.example.bookrental.Entity.Author;
import com.example.bookrental.Service.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;

    @PostMapping("/addAuthor")
    public Author addAuthor (@RequestBody AuthorDto authorDto){
        return authorService.addAuthor(authorDto);
    }
    @PutMapping("/updateAuthor")
    public Author updateAuthor (@RequestBody AuthorDto authorDto){
        return authorService.updateAuthor(authorDto);
    }
    @GetMapping("/allAuthors")
    public List<Author> getAllAuthor (){
        return authorService.getAllAuthor();
    }
    @DeleteMapping("/delete")
    public String deleteAuthor (@RequestParam long id){
        return authorService.deleteAuthor(id);
    }
}

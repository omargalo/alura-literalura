package com.alura.literalura;

import com.alura.literalura.model.Book;
import com.alura.literalura.service.GutendexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	@Autowired
	private GutendexService gutendexService;

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Scanner teclado = new Scanner(System.in);
		muestraElMenu(teclado);
	}

	public void muestraElMenu(Scanner teclado) {
		var opcion = -1;
		while (opcion != 0) {
			var menu = """
                    1 - Buscar libros 
                    2 - Buscar autor
                    3 - Guardar libro
                    4 - Opcion 4
                    5 - Opcion 5

                    0 - Salir
                    """;
			System.out.println(menu);
			opcion = teclado.nextInt();
			teclado.nextLine();

			switch (opcion) {
				case 1:
					buscarLibros(teclado);
					break;
				case 2:
					buscarAutor(teclado);
					break;
				case 3:
					guardarLibro(teclado);
					break;
				case 4:
					opcion4();
					break;
				case 5:
					opcion5();
					break;
				case 0:
					System.out.println("Cerrando la aplicación...");
					break;
				default:
					System.out.println("Opción inválida");
			}
		}
	}

	private void buscarLibros(Scanner teclado) {
		System.out.println("Ingrese el término de búsqueda:");
		String query = teclado.nextLine();
		var libros = gutendexService.buscarLibros(query);
		libros.forEach(libro -> System.out.println(libro.getTitle()));
	}

	private void buscarAutor(Scanner teclado) {
		// Implementación futura
	}

	private void guardarLibro(Scanner teclado) {
		System.out.println("Ingrese el término de búsqueda del libro a guardar:");
		String query = teclado.nextLine();
		var libros = gutendexService.buscarLibros(query);
		if (!libros.isEmpty()) {
			Book libro = libros.get(0); // Seleccionamos el primer libro encontrado
			gutendexService.guardarLibro(libro);
			System.out.println("Libro guardado: " + libro.getTitle());
		} else {
			System.out.println("No se encontraron libros con el término de búsqueda proporcionado.");
		}
	}

	private void opcion4() {
		// Implementación futura
	}

	private void opcion5() {
		// Implementación futura
	}
}

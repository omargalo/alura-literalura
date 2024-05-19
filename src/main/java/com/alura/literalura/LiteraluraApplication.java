package com.alura.literalura;

import com.alura.literalura.model.AuthorEntity;
import com.alura.literalura.model.Book;
import com.alura.literalura.model.BookEntity;
import com.alura.literalura.service.GutendexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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
                    ******************************************
                    1 - Buscar libros en Gutendex
                    2 - Buscar autor en Gutendex
                    3 - Listar libros de mi colección
                    4 - Listar autores de mi colección
                    5 - Listar top 5 libros de mi colección
                    6 - Listar libros de mi colección por idioma
                    7 - Listar autores vivos en un año especifico

                    0 - Salir
                    ******************************************
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
					listarMisLibros();
					break;
				case 4:
					listarMisAutores();
					break;
				case 5:
					listarMiTop();
					break;
				case 6:
					listarLibrosPorIdioma(teclado);
					break;
				case 7:
					listarAutoresVivos(teclado);
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
		System.out.println("****************************");
		System.out.println("Ingrese el libro a buscar:");
		String query = teclado.nextLine().toLowerCase();
		var libros = gutendexService.buscarLibros(query);

		if (libros.isEmpty()) {
			System.out.println("No se encontraron libros con el título de búsqueda proporcionado.");
			return;
		}

		System.out.println("Libros encontrados:");
		for (int i = 0; i < libros.size(); i++) {
			Book libro = libros.get(i);
			System.out.println("ID: " + libro.getId() + " - Título: " + libro.getTitle() + " - Autor: " + libro.getAuthors().get(0).getName());
		}

		System.out.println("¿Desea guardar alguno de estos libros en la colección? (si/no)");
		String respuesta = teclado.nextLine();

		if (respuesta.equalsIgnoreCase("si")) {
			System.out.println("Ingrese el ID del libro que desea guardar:");
			int idLibro = teclado.nextInt();
			teclado.nextLine(); // consume newline

			Book libroSeleccionado = libros.stream()
					.filter(libro -> libro.getId() == idLibro)
					.findFirst()
					.orElse(null);

			if (libroSeleccionado != null) {
				gutendexService.guardarLibro(libroSeleccionado);
				System.out.println("Libro guardado: " + libroSeleccionado.getTitle());
			} else {
				System.out.println("ID de libro inválido.");
			}
		}
	}

	private void buscarAutor(Scanner teclado) {
		System.out.println("****************************");
		System.out.println("Ingrese el nombre del autor:");
		String autor = teclado.nextLine().toLowerCase();
		var libros = gutendexService.buscarLibrosPorAutor(autor);

		if (libros.isEmpty()) {
			System.out.println("No se encontraron libros para el autor proporcionado.");
			return;
		}

		System.out.println("Libros encontrados:");
		for (int i = 0; i < libros.size(); i++) {
			Book libro = libros.get(i);
			System.out.println("ID: " + libro.getId() + " - Título: " + libro.getTitle() + " - Autor: " + libro.getAuthors().get(0).getName());
		}

		System.out.println("¿Desea guardar alguno de estos libros en la colección? (si/no)");
		String respuesta = teclado.nextLine();

		if (respuesta.equalsIgnoreCase("si")) {
			System.out.println("Ingrese el ID del libro que desea guardar:");
			int idLibro = teclado.nextInt();
			teclado.nextLine(); // consume newline

			Book libroSeleccionado = libros.stream()
					.filter(libro -> libro.getId() == idLibro)
					.findFirst()
					.orElse(null);

			if (libroSeleccionado != null) {
				gutendexService.guardarLibro(libroSeleccionado);
				System.out.println("Libro guardado: " + libroSeleccionado.getTitle());
			} else {
				System.out.println("ID de libro inválido.");
			}
		}
	}

	private void listarMisLibros() {
		List<BookEntity> misLibros = gutendexService.listarMisLibros();
		if (misLibros.isEmpty()) {
			System.out.println("No hay libros en tu colección.");
			return;
		}

		System.out.println("****************************");
		System.out.println("Libros en tu colección:");
		for (BookEntity libro : misLibros) {
			Collectors Collectors = null;
			System.out.println("ID: " + libro.getId() + " - Título: " + libro.getTitle() + " - Autor(es): " + libro.getAuthors().stream()
					.map(AuthorEntity::getName)
					.collect(Collectors.joining(", ")));
		}
	}

	private void listarMisAutores() {
		List<AuthorEntity> misAutores = gutendexService.listarMisAutores();
		if (misAutores.isEmpty()) {
			System.out.println("No hay autores en tu colección.");
			return;
		}

		System.out.println("****************************");
		System.out.println("Autores en tu colección:");
		for (AuthorEntity autor : misAutores) {
			System.out.println("Nombre: " + autor.getName() + " - Año de nacimiento: " + autor.getBirthYear() + " - Año de fallecimiento: " + autor.getDeathYear());
		}
	}

	private void listarMiTop() {
		List<BookEntity> topLibros = gutendexService.listarTopLibros();
		if (topLibros.isEmpty()) {
			System.out.println("No hay libros en tu colección.");
			return;
		}

		System.out.println("****************************");
		System.out.println("Top 5 libros en tu colección:");
		for (BookEntity libro : topLibros) {
			System.out.println("ID: " + libro.getId() + " - Título: " + libro.getTitle() + " - Descargas: " + libro.getDownloadCount());
		}
	}

	private void listarLibrosPorIdioma(Scanner teclado) {
		System.out.println("****************************");
		System.out.println("Ingrese en para inglés, es para español):");
		String idioma = teclado.nextLine().toLowerCase();
		List<BookEntity> librosPorIdioma = gutendexService.listarLibrosPorIdioma(idioma);
		if (librosPorIdioma.isEmpty()) {
			System.out.println("No hay libros en el idioma especificado.");
			return;
		}

		System.out.println("****************************");
		System.out.println("Libros en " + idioma + ":");
		for (BookEntity libro : librosPorIdioma) {
			System.out.println("ID: " + libro.getId() + " - Título: " + libro.getTitle() + " - Autor(es): " + libro.getAuthors().stream()
					.map(AuthorEntity::getName)
					.collect(Collectors.joining(", ")));
		}
	}

	private void listarAutoresVivos(Scanner teclado) {
		System.out.println("****************************");
		System.out.println("Ingrese el año:");
		while (!teclado.hasNextInt()) {
			System.out.println("Entrada inválida. Por favor, ingrese un año válido.");
			teclado.next(); // consume el input inválido
		}
		int year = teclado.nextInt();
		teclado.nextLine(); // consume newline

		List<AuthorEntity> autoresVivos = gutendexService.listarAutoresVivosEnAno(year);
		if (autoresVivos.isEmpty()) {
			System.out.println("No hay autores vivos en el año especificado.");
			return;
		}

		System.out.println("****************************");
		System.out.println("Autores vivos en el año " + year + ":");
		for (AuthorEntity autor : autoresVivos) {
			System.out.println("Nombre: " + autor.getName() + " - Año de nacimiento: " + autor.getBirthYear() + " - Año de fallecimiento: " + autor.getDeathYear());
		}
	}

}

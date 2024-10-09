package entidades;

public class Libro {
	
	private String ISBN;
	private String titulo;
	private Autor autor;
	
	public Libro() {
        
    }
	
	public Libro(String iSBN, String titulo, Autor autor) {
		this.ISBN = iSBN;
        this.titulo = titulo;
        this.autor = autor;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	@Override
	public String toString() {
		return "Libro [ISBN=" + ISBN + ", titulo=" + titulo + ", autor=" + autor + "]";
	}
	
	

}

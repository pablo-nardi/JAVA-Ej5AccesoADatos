package ui;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

import entities.*;
import logic.Login;

public class Menu {
	Scanner s=null;
	Login ctrlLogin = new Login();

	public void start() {
		s = new Scanner(System.in);
		Persona p=login();
		System.out.println("Bienvenido "+p.getNombre()+" "+p.getApellido());
		System.out.println();
		
		String command;
		do {
			command=getCommand();
			executeCommand(command);
			System.out.println(); 
			
		}while(!command.equalsIgnoreCase("exit"));
		
		s.close();
	}

	private void executeCommand(String command) {
		switch (command) {
		case "list":
			System.out.println(ctrlLogin.getAll());
			break;
		case "find":
			System.out.println(find());
			break;
		case "search":
			 System.out.println(search());
			break;
		case "new":
			setUser();
			break;
		case "edit":
			edit();
			break;
		case "delete":
			delete();
			break;
		default:
			break;
		}
	}

	private String getCommand() {
		System.out.println("Ingrese el comando según la opción que desee realizar");
		System.out.println("list\t\tlistar todos");
		System.out.println("find\t\tbuscar por tipo y nro de documento"); //solo debe devolver 1
		System.out.println("search\t\tlistar por apellido"); //puede devolver varios	HECHO	
		System.out.println("new\t\tcrea una nueva persona y asigna un rol existente");// HECHO, pero falta arreglar add_rol_persona
		System.out.println("edit\t\tbusca por tipo y nro de documento y actualiza todos los datos");
		//FALTA QUE MUESTRE TODOS LOS USUARIOS QUE CONICIDEN CON ESE DNI --> Creo que el profe no lo contempla
		//no realiza el delete de los roles que no se necesitan
		//¿Se usa executeUpdate con los delete?
		
		System.out.println("delete\t\tborra por tipo y nro de documento");
		System.out.println();
		System.out.print("comando: ");
		return s.nextLine();
	}
	
	public Persona login() {
		Persona p=new Persona();
		
		/*System.out.print("Email: ");
		p.setEmail(s.nextLine());

		System.out.print("password: ");
		p.setPassword(s.nextLine());
		*/
		p.setEmail("jp@gmail.com");
		p.setPassword("jperez");
		p=ctrlLogin.validate(p);
		
		return p;
		
	}
	
	private Persona find() {
		System.out.println();
		Persona p=new Persona();
		Documento d=new Documento();
		p.setDocumento(d);
		System.out.print("Tipo doc: ");
		d.setTipo(s.nextLine());

		System.out.print("Nro doc: ");
		d.setNro(s.nextLine());
		
		return ctrlLogin.getByDocumento(p);
	}
	private LinkedList<Persona> search(){
		System.out.println();
		Persona p = new Persona();
		Documento d = new Documento();
		p.setDocumento(d);
		
		System.out.println("Ingrese apellido: ");
		p.setApellido(s.nextLine());
		
		return ctrlLogin.getByApellido(p);
	}
	private void setUser() {
		Persona p = new Persona();
		Documento d = new Documento();
		p.setDocumento(d);
		Rol r = new Rol();
		

		
		System.out.println("Ingrese tipo de Documento: (ej: DNI, CUITm CUIL)");
		d.setTipo(s.nextLine());
		
		System.out.println("Ingrese numero de Documento: ");
		d.setNro(s.nextLine());
		
		System.out.println("Ingrese nombre: ");
		p.setNombre(s.nextLine());
		
		System.out.println("Ingrese apellido: ");
		p.setApellido(s.nextLine());
		
		System.out.println("Ingrese mail: ");
		p.setEmail(s.nextLine());
		
		System.out.println("Ingrese telefono: ");
		p.setTel(s.nextLine());
		
		System.out.println("Ingrese contraseña del usuario: ");
		p.setPassword(s.nextLine());
		
		System.out.println("Es un usuario habilitado?\n1-Si\n0-No ");
		int resp = s.nextInt();
		s.nextLine();
		if(resp == 0) {
			p.setHabilitado(false);
		}else {
			p.setHabilitado(true);
		}
		System.out.println("Que Rol tiene?\n1-admin\n2-user\n3-ambos");
		int resp2 = s.nextInt();
		s.nextLine();
		if(resp2 == 1) {
			r.setId(1);
			r.setDescripcion("admin");
			p.addRol(r);
		}else if(resp2==2) {
			r.setId(2);
			r.setDescripcion("user");
			p.addRol(r);
		} 
		else if(resp2==3) {
			Rol r2 = new Rol();
			r.setId(1);
			r.setDescripcion("admin");
			p.addRol(r);
		
			r2.setId(2);
			r2.setDescripcion("user");
			p.addRol(r2);
		}
		
		
		ctrlLogin.setUser(p);
		System.out.println(ctrlLogin.getAll());/**/
		
	}
	private void edit() {
		
		System.out.println();
		Persona p=new Persona();
		Documento d=new Documento();
		p.setDocumento(d);
		System.out.println("Primero busquemos el usuario:");
		System.out.print("Tipo doc: ");
		d.setTipo(s.nextLine());
		System.out.print("Nro doc: ");
		d.setNro(s.nextLine());
		HashMap<Integer, Rol> roles;
		roles = new HashMap<>();
		

		System.out.println(ctrlLogin.getByDocumento(p));
		
		//System.out.println(ctrlLogin.getByDocumento(p));
		String resp;
		
		do {
			System.out.println("");
			System.out.println("CUIDADO! Antes de borrar deberá saber la contraseña del usuario a modificar\n");
			System.out.println("¿Que valor desea cambiar?\n");
			System.out.println("Escriba 'exit' para salir\n");
			System.out.println("dni\t\tpara cambiar el dni");
			System.out.println("nombre\t\tpara cambiar el nombre");
			System.out.println("apellido\t\tpara cambiar apellido");
			System.out.println("mail\t\tpara cambiar el mail");
			System.out.println("telefono\t\tpara cambiar el telefono");
			System.out.println("contraseña\t\tpara cambiar contraseña");
			System.out.println("estado\t\tpara cambiar el estado");
			System.out.println("rol\t\tpara cambiar el rol");
			resp = s.nextLine();
			
			switch(resp) {
			case "dni":
				System.out.println("Ingrese tipo del nuevo dni:");
				p.getDocumento().setTipo(s.nextLine());
				System.out.println("Ingrese numero del documento:");
				p.getDocumento().setNro(s.nextLine());
				break;
			case "nombre":
				System.out.println("Ingrese nuevo nombre:");
				p.setNombre(s.nextLine());
				break;
			case "apellido":
				System.out.println("Ingrese nuevo apellido:");
				p.setApellido(s.nextLine());
				break;
			case "mail":
				System.out.println("Ingrese nuevo mail:");
				p.setEmail(s.nextLine());
				break;
			case "telefono":
				System.out.println("Ingrese nuevo telefono:");
				p.setTel(s.nextLine());
				break;
			case "contraseña":
				System.out.println("Ingrese nueva contraseña:");
				p.setPassword(s.nextLine());
				break;
			case "estado":
				System.out.println("Es un usuario habilitado?\n1-Si\n0-No ");
				int val = s.nextInt();
				s.nextLine();
				if(val == 0) {
					p.setHabilitado(false);
				}else {
					p.setHabilitado(true);
				}
				break;
			case "rol":
				System.out.println("Que Rol tiene?\n1-admin\n2-user\n3-ambos");
				Rol r = new Rol();
				int resp2 = s.nextInt();
				s.nextLine();
				if(resp2 == 1) {
					r.setId(1);
					r.setDescripcion("admin");
					roles.put(r.getId(), r);
				}else if(resp2==2) {
					r.setId(2);
					r.setDescripcion("user");
					p.addRol(r);
					roles.put(r.getId(), r);
				} 
				else if(resp2==3) {
					Rol r2 = new Rol();
					r.setId(1);
					r.setDescripcion("admin");
					p.addRol(r);
					
					r2.setId(2);
					r2.setDescripcion("user");
					p.addRol(r2);
					
					roles.put(r.getId(), r);
					roles.put(r.getId(), r2);
				}
				break;
			default:
				break;
			}
			
		}while(!resp.equalsIgnoreCase("exit"));
		//System.out.println(p);
		System.out.println("Ingrese contraseña del usuario a cambiar:");
		p.setPassword(s.nextLine());
		ctrlLogin.updateUser(p, roles);
	}
	private void delete() {
		
		Persona per=new Persona();
		Documento d=new Documento();
		per.setDocumento(d);
		System.out.println("Primero busquemos el usuario:");
		System.out.print("Tipo doc: ");
		d.setTipo(s.nextLine());
		System.out.print("Nro doc: ");
		d.setNro(s.nextLine());
		
		ctrlLogin.deleteUser(per);
	}

}

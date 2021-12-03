#import Crud1 as conn
import sqlite3
#db = conn.Crud1()

def crear():
    # a la base creada se le ingresa datos por teclado
    bd = sqlite3.connect("agenda.db")
    cursor = bd.cursor()
    nombre = input("\nnombre: ")
    email = input("\nemail: ")
    telefono = float(input("\ntelefono: "))

    sentencia = "INSERT INTO agendados(nombre, email, telefono) VALUES (?,?,?)"
    cursor.execute(sentencia, [nombre, email, telefono])
    bd.commit()
    bd.close()
    print("Guardado correctamente")

def leer_registro():
    # se lee la base de datos, los datos ingresados se muestra por pantalla
    con = sqlite3.connect("agenda.db")
    cursor = con.execute("select id, nombre, email, telefono from agendados")
    for fila in cursor:
        print(fila)
    con.close()

def actualizar_registros():
    # se busca un id y se le cambian los datos
    try:
        con = sqlite3.connect("agenda.db")
        cursor = con.execute("select id, nombre, email, telefono from agendados")
        print("id, nombre, email, telefono")
        for fila in cursor:
            #print("id, nombre, email, telefono")
            print(fila)
        id = input("\nEscribe el id del libro que quieres editar: ")
        if not id:
            print("No escribiste nada")
            exit()
        nombre = input("\nnombre: ")
        email = input("\nemail: ")
        telefono = float(input("\ntelefono: "))
        sentencia = "UPDATE agendados SET nombre = ?, email = ?, telefono = ? WHERE rowid = ?;"

         # Actualizar datos
        cursor.execute(sentencia, [nombre, email, telefono, id])
        con.commit()
        print("Datos guardados")

        con.close()

    except sqlite3.OperationalError as error:
        print("Error al abrir:", error)

def eliminar_registros():
    #se elimina algun dato(fila) seleccionado
    try:
        con = sqlite3.connect("agenda.db")
        cursor = con.execute("select id, nombre, email, telefono from agendados")
        print("id, nombre, email, telefono")
        for fila in cursor:
            #print("id, nombre, email, telefono")
            print(fila)

        id = input("\nEscribe el id del libro que quieres editar: ")

        if not id:
            print("No escribiste nada")
            exit()

        sentencia = "DELETE FROM agendados WHERE rowid = ?;"

        # Actualizar datos
        cursor.execute(sentencia, [id])
        con.commit()
        print("Datos eliminados")

    except sqlite3.OperationalError as error:
        print("Error al abrir:", error)
def buscar_registros():
    # se busca un dato especifico a traves del teclado
    con = sqlite3.connect("agenda.db")

    nombre = input("ingrese nombre de la persona: ")
    cursoNormal = con.execute("select nombre, email, telefono from agendados where nombre==?", (nombre,))

    filas = cursoNormal.fetchall()

    if len(filas) > 0:
        for fila in filas:
            print(fila)
    else:
        print("no esta registrada la persona")
    con.close()
while True:
    print("crud en python")
    print("1 insertar registros")
    print("2 listar registros")
    print("3 actualizar registros")
    print("4 eliminar registros")
    print("5 buscar registros")
    print("6 salir")

    try:
        opciones = int(input("ingrese una opcion"))
        if opciones == 1:
            crear()
        elif opciones == 2:
            leer_registro()
        elif opciones == 3:
            actualizar_registros()
        elif opciones == 4:
            eliminar_registros()
        elif opciones == 5:
            buscar_registros()
        elif opciones == 6:
            break
    except:
        print("opcion incorrecta")

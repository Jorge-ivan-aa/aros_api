# Git Policies

En el presente documento se establecen las politicas de uso de git para mantener la integridad del proyecto. Los repositorios estarán divididos en dos (2) ramas `main` y `dev`.

## La Rama main
En la rama main se encontraran solamente las versiones “estables” que hayan sido probadas evitando subir cambios que aún se encuentren en desarrollo, es decir, no hayan sido terminamos o no sean 100% funcionales (con excepciones especiales). Todos los cambios que se hagan en esta rama deben ser aprobados por los integrantes del equipo.

## La Rama dev
En la rama dev se subirán los cambios que aún se encuentran en desarrollo y también aquellos cambios probados y que están a la espera de la aprobación por parte del equipo, a pesar de ello debe evitarse trabajar directamente sobre la rama dev y en su lugar usarla para derivar otra rama que posteriormente será integrada a la rama dev.


## Convenciones y estándares
Haremos uso de algunas convenciones y estándares para asegurar una cohesividad entre los miembros del equipo:

- Formato `story/<Código de la Historia>` para nombrar las ramas que estén implementando una nueva funcionalidad.

- Formato `hotfix/<Titulo descriptivo>` para nombrar las ramas que realicen cambios rápidos o urgentes.

### Mensajes de commit

 Los commits deben llevar mensajes y estos han de ser escritos en inglés, deberán ser autoexplicativos y concisos, en caso de realizar varias modificaciones y que estas sean difíciles de escribir de forma concisa, el sujeto del mensaje corresponderá al cambio principal que lleva a realizar estas modificaciones y se incluirá un cuerpo que lista los cambios realizados.

- **feature**: Se utiliza cuando se completa el desarrollo de una nueva funcionalidad del sistema. Si la funcionalidad corresponde a una tarea derivada de una historia de usuario, se debe incluir el código de la tarea en el mensaje.
    Ejemplo:
        `feature(CO-23): implement user login with authentication`


- **refactor**: Indica que se realizó una mejora o reestructuración del código existente sin alterar su comportamiento funcional. El mensaje debe incluir el motivo del cambio y especificar el elemento afectado.
    Ejemplo:
        `refactor(user-service): simplify authentication logic for clarity`

- **fix**: Se usa para señalar la corrección de un bug o error detectado en el sistema.
    Ejemplo:
        `fix(api): resolve null pointer exception in user controller`

- **chore**: Se emplea para registrar tareas de configuración, mantenimiento o ajustes que no afectan directamente la funcionalidad del sistema.
    Ejemplo:
        `chore: configure ESLint and Prettier`

- **docs**: Indica modificaciones o adiciones relacionadas con la documentación del proyecto.
    Ejemplo:
        `docs(readme): update setup instructions explicar que se hizo algo relacionado con la documentación.`


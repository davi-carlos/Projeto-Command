// Interface Command
public interface Command {
    void execute();
    void undo();
}

// Receivers
public class Light {
    public void on() {
        System.out.println("Luz LIGADA");
    }

    public void off() {
        System.out.println("Luz DESLIGADA");
    }
}

public class GarageDoor {
    public void open() {
        System.out.println("Garagem ABERTA");
    }

    public void close() {
        System.out.println("Garagem FECHADA");
    }
}

// Implementação dos comandos
public class LightOnCommand implements Command {
    private Light light;

    public LightOnCommand(Light light) {
        this.light = light;
    }

    public void execute() {
        light.on();
    }

    public void undo() {
        light.off();
    }
}

public class GarageOpenCommand implements Command {
    private GarageDoor garage;

    public GarageOpenCommand(GarageDoor garage) {
        this.garage = garage;
    }

    public void execute() {
        garage.open();
    }

    public void undo() {
        garage.close();
    }
}

// Invoker
public class RemoteControl {
    private Command command;
    private Command lastCommand;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void pressButton() {
        command.execute();
        lastCommand = command;
    }

    public void pressUndo() {
        if (lastCommand != null) {
            lastCommand.undo();
        }
    }
}

// Client
public class Main {
    public static void main(String[] args) {
        Light light = new Light();
        GarageDoor garage = new GarageDoor();

        Command ligarLuz = new LightOnCommand(light);
        Command abrirGaragem = new GarageOpenCommand(garage);

        RemoteControl remote = new RemoteControl();

        remote.setCommand(ligarLuz);
        remote.pressButton();   // Luz LIGADA
        remote.pressUndo();     // Luz DESLIGADA

        remote.setCommand(abrirGaragem);
        remote.pressButton();   // Garagem ABERTA
        remote.pressUndo();     // Garagem FECHADA
    }
}
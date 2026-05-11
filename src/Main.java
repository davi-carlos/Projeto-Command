import java.util.Arrays;
import java.util.List;

public class Main {


    // STRATEGY

    interface ActionStrategy {
        void execute();
    }


    // COMMAND
 
    interface Command {
        void execute();
        void undo();
    }

    // RECEIVERS

    static class Light {

        public void on() {
            System.out.println("Luz LIGADA");
        }

        public void off() {
            System.out.println("Luz DESLIGADA");
        }
    }

    static class GarageDoor {

        public void open() {
            System.out.println("Garagem ABERTA");
        }

        public void close() {
            System.out.println("Garagem FECHADA");
        }
    }

    // STRATEGIES

    static class LightOnStrategy implements ActionStrategy {

        private Light light;

        public LightOnStrategy(Light light) {
            this.light = light;
        }

        @Override
        public void execute() {
            light.on();
        }
    }

    static class GarageOpenStrategy implements ActionStrategy {

        private GarageDoor garage;

        public GarageOpenStrategy(GarageDoor garage) {
            this.garage = garage;
        }

        @Override
        public void execute() {
            garage.open();
        }
    }

    // Macro Strategy
    static class MacroStrategy implements ActionStrategy {

        private List<ActionStrategy> actions;

        public MacroStrategy(List<ActionStrategy> actions) {
            this.actions = actions;
        }

        @Override
        public void execute() {
            for (ActionStrategy action : actions) {
                action.execute();
            }
        }
    }

    // COMMANDS

    static class LightOnCommand implements Command {

        private Light light;

        public LightOnCommand(Light light) {
            this.light = light;
        }

        @Override
        public void execute() {
            light.on();
        }

        @Override
        public void undo() {
            light.off();
        }
    }

    static class GarageOpenCommand implements Command {

        private GarageDoor garage;

        public GarageOpenCommand(GarageDoor garage) {
            this.garage = garage;
        }

        @Override
        public void execute() {
            garage.open();
        }

        @Override
        public void undo() {
            garage.close();
        }
    }

    // Macro Command
    static class MacroCommand implements Command {

        private List<Command> commands;

        public MacroCommand(List<Command> commands) {
            this.commands = commands;
        }

        @Override
        public void execute() {
            for (Command command : commands) {
                command.execute();
            }
        }

        @Override
        public void undo() {
            for (Command command : commands) {
                command.undo();
            }
        }
    }

  
    // INVOKER STRATEGY

    static class StrategyRemoteControl {

        private ActionStrategy strategy;

        public void setStrategy(ActionStrategy strategy) {
            this.strategy = strategy;
        }

        public void pressButton() {
            strategy.execute();
        }
    }

 
    // INVOKER COMMAND
 
    static class CommandRemoteControl {

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

 
    // MAIN
    
    public static void main(String[] args) {

        Light light = new Light();
        GarageDoor garage = new GarageDoor();

      
        // STRATEGY
   

        ActionStrategy ligarLuzStrategy =
                new LightOnStrategy(light);

        ActionStrategy abrirGaragemStrategy =
                new GarageOpenStrategy(garage);

        ActionStrategy macroStrategy =
                new MacroStrategy(
                        Arrays.asList(
                                ligarLuzStrategy,
                                abrirGaragemStrategy
                        )
                );

        StrategyRemoteControl strategyRemote =
                new StrategyRemoteControl();

        System.out.println("=== STRATEGY ===");

        strategyRemote.setStrategy(macroStrategy);
        strategyRemote.pressButton();

       
        // COMMAND
      

        Command ligarLuzCommand =
                new LightOnCommand(light);

        Command abrirGaragemCommand =
                new GarageOpenCommand(garage);

        Command macroCommand =
                new MacroCommand(
                        Arrays.asList(
                                ligarLuzCommand,
                                abrirGaragemCommand
                        )
                );

        CommandRemoteControl commandRemote =
                new CommandRemoteControl();

        System.out.println("\n=== COMMAND ===");

        commandRemote.setCommand(macroCommand);

        commandRemote.pressButton();

        System.out.println("\nUNDO:");

        commandRemote.pressUndo();
    }
}
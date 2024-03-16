package commands;

import context.Context;
import exceptions.CommandException;

import java.util.List;

public interface Command {
    void execute(Context context, String[] args) throws CommandException;
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartclass.services;

import context.arch.comm.DataObject;
import context.arch.service.Service;
import context.arch.service.helper.FunctionDescription;
import context.arch.service.helper.FunctionDescriptions;
import context.arch.service.helper.ServiceInput;
import context.arch.widget.Widget;

/**
 *
 * @author Pedro
 */
public class LightService extends Service {

    public LightService(final Widget widget) {
        super(widget, "LightService",
                new FunctionDescriptions() {
            {
                add(new FunctionDescription(
                        "lightControl",
                        "Sets the light level of the lamp",
                        widget.getNonConstantAttributes()));
            }
        });
    }

    @Override
    public DataObject execute(ServiceInput si) {
        int light = si.getInput().getAttributeValue("light");
        System.out.println("Service: "+light);
        return new DataObject(); // no particular info to return
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartclass.enactor;

import context.apps.demos.roomlight.LightWidget;
import static context.apps.demos.roomlight.RoomEnactor.BRIGHTNESS_THRESHOLD;
import context.arch.discoverer.ComponentDescription;
import context.arch.discoverer.component.NonConstantAttributeElement;
import context.arch.discoverer.query.AbstractQueryItem;
import context.arch.discoverer.query.ElseQueryItem;
import context.arch.discoverer.query.ORQueryItem;
import context.arch.discoverer.query.RuleQueryItem;
import context.arch.discoverer.query.comparison.AttributeComparison;
import context.arch.enactor.Enactor;
import context.arch.enactor.EnactorReference;
import context.arch.service.helper.ServiceInput;
import context.arch.storage.AttributeNameValue;
import context.arch.storage.Attributes;
import context.arch.widget.Widget;
import context.arch.widget.Widget.WidgetData;

/**
 *
 * @author Pedro
 */
public class RoomEnactor extends Enactor {

    public RoomEnactor(AbstractQueryItem<?, ?> inWidgetSubscriptionQuery, AbstractQueryItem<?, ?> outWidgetSubscriptionQuery, String outcomeName, String shortId) {
        super(inWidgetSubscriptionQuery, outWidgetSubscriptionQuery, outcomeName, shortId);

        AbstractQueryItem<?, ?> offQI
                = new ORQueryItem(
                        RuleQueryItem.instance(
                                new NonConstantAttributeElement(AttributeNameValue.instance("presence", 0)),
                                new AttributeComparison(AttributeComparison.Comparison.EQUAL)),
                        RuleQueryItem.instance(
                                new NonConstantAttributeElement(AttributeNameValue.instance("brightness", 50)),
                                new AttributeComparison(AttributeComparison.Comparison.GREATER))
                );
        
        EnactorReference er = new RoomEnactorReference(
                offQI,
                "Off");
        er.addServiceInput(new ServiceInput("LightService", "lightControl",
                new Attributes() {
            {
                addAttribute("light", Integer.class);
            }
        }));
        addReference(er);

        // light on, and brightness dependent
        er = new RoomEnactorReference(
                new ElseQueryItem(offQI),
                "On");
        er.addServiceInput(new ServiceInput("LightService", "lightControl",
                new Attributes() {
            {
                addAttribute("light", Integer.class);
            }
        }));
        addReference(er);

        start();

    }
    
    private class RoomEnactorReference extends EnactorReference {

        public RoomEnactorReference(AbstractQueryItem<?, ?> conditionQuery, String outcomeValue) {
            super(RoomEnactor.this, conditionQuery, outcomeValue);
        }

        @Override
        protected Attributes conditionSatisfied(ComponentDescription inWidgetState, Attributes outAtts) {
            long timestamp = outAtts.getAttributeValue(Widget.TIMESTAMP);
            WidgetData data = new WidgetData("LightWidget", timestamp);
            int light;
            if ("On".equals(outcomeValue)) {

                //short brightness = inWidgetState.getAttributeValue("brightness");
                light = 1;
            } else {
                light = 0;
            }

            data.setAttributeValue("light", light);
            outAtts.putAll(data.toAttributes());
            
            return outAtts;
        }

    }
}

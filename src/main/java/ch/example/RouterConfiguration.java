package ch.example;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ch.example.incident.IncidentService;
import ch.example.incident.InputReportIncident;
import ch.example.incident.OutputReportIncident;
import ch.example.incident.OutputStatusIncident;

@Configuration
public class RouterConfiguration {

	@Bean
	RoutesBuilder myRouter() {
		return new RouteBuilder() {

			@Override
			public void configure() throws Exception {
				// Access us using http://localhost:8080/camel/hello
				from("servlet:///hello").transform().constant(
						"Hello from Camel!");

				// cxf example
				from("cxf:/incident?serviceClass=" + IncidentService.class.getName())
						.to("log:input")
						// send the request to the route to handle the operation
						// the name of the operation is in that header
						.recipientList(simple("direct:${header.operationName}"));

				// report incident
				from("direct:reportIncident").process(new Processor() {
					public void process(final Exchange exchange) throws Exception {
						// get the id of the input
						final String id = exchange.getIn()
								.getBody(InputReportIncident.class)
								.getIncidentId();

						// set reply including the id
						final OutputReportIncident output = new OutputReportIncident();
						output.setCode("OK;" + id);
						exchange.getOut().setBody(output);
					}
				}).to("log:output");

				// status incident
				from("direct:statusIncident").process(new Processor() {
					public void process(final Exchange exchange) throws Exception {
						// set reply
						final OutputStatusIncident output = new OutputStatusIncident();
						output.setStatus("IN PROGRESS");
						exchange.getOut().setBody(output);
					}
				}).to("log:output");

			}

		};
	}

}
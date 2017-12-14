package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.data.JobFieldData;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.swing.text.Position;
import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, @RequestParam int id) { //Request param grabs the query param

        // TODO #1 - get the Job with the given ID and pass it into the view

        Job job = jobData.findById(id);

        model.addAttribute("job", job);
        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.

        JobData jobData = JobData.getInstance();

        if (errors.hasErrors()){ //Checks to see if the JobForm object is valid
            return "new-job";
        }

        String jobName = jobForm.getName();
        Employer jobEmployer = jobForm.getEmployer();
        Location jobLocation = jobForm.getLocation();
        PositionType jobPosition = jobForm.getPositionType();
        CoreCompetency jobSkill = jobForm.getCoreCompetency();

        Job newJob = new Job(jobName, jobEmployer, jobLocation, jobPosition, jobSkill);

        jobData.add(newJob);



        return "redirect:/job/?id=" + newJob.getId();

    }
}

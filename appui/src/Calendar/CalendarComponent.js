import React, { Component } from "react";
import Calendar from "./Calendar";
import { Row } from "react-bootstrap";
import { translate, Trans } from 'react-i18next';
import ExistingNotes from "./ExistingNotes";
import AddNote from "./AddNote";
const style = {
    position: "relative",
    margin: "50px auto"
}

class CalendarComponent extends Component {
    constructor(props) {
        super(props);
        this.state = {
            date: {
                day: 0,
                month: "",
                year: 0
            },
            eventsDates: [],
            projects: []
        }
    }
    myCallback = (day, month, year) => {
        const date = this.state.date
        date.day = day
        date.month = month
        date.year = year
        this.setState({ date: date })
    }
    componentDidMount() {
        const URL = "http://localhost:8080"
        fetch(URL + "/calendar/all",{
            headers:{
                'Authorization': 'Basic ' + btoa(this.props.credentials.username + ":" + this.props.credentials.password)
            }
        }).then(res => res.json())
            .then(data => {
                this.setState({ eventsDates: data })
            });
        fetch(URL + "/projects/all",{
            headers:{
                'Authorization': 'Basic ' + btoa(this.props.credentials.username + ":" + this.props.credentials.password)
            }
        }).then(res => res.json())
            .then(data => {
                this.setState({ projects: data })
            })
    }

    onDayClick = (e, day) => {
    }

    render() {
        return (
            <div>
                <Row className="App">
                    <Calendar style={style} width="auto"
                              onDayClick={(e, day)=> this.onDayClick(e, day)}
                              selectedDay={this.state.selectedDay}
                              callbackFromParent={this.myCallback}
                              notesAt={this.state.eventsDates}
                    />
                </Row>
                <Row>
                    <b><u>{this.props.t('calendar.existing', { framework: "react-i18next" })}</u></b>
                    <ExistingNotes
                        date={this.state.date}
                        credentials={this.props.credentials}
                    />
                </Row>
                <Row>
                    <b><u>{this.props.t('calendar.new', { framework: "react-i18next" })}</u></b>
                    <AddNote
                        selectedDate={this.state.selectedDate}
                        projects={this.state.projects}
                        buttonStyle={this.props.buttonStyle}
                        credentials={this.props.credentials}
                    />
                </Row>

            </div>
        );
    }
}


export default translate('common')(CalendarComponent);
import React, { Component } from "react";
import MainComponent from "../Main/MainComponent";
import ReportedIssues from "../reportedIssues/ReportedIssues";
import AddIssue from "../AddNewIssue/AddIssue";
import CalendarComponent from "../Calendar/CalendarComponent";
import Messages from "../Messages/Messages";
import { translate, Trans } from 'react-i18next';
import Projects from "../Projects/Projects";
import NewUser from "../NewUser";
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import CircularProgress from 'material-ui/CircularProgress';

class DisplayComponent extends Component {
    renderSwitch() {
        switch(this.props.index){
            default:
            case 0:
                return(
                    <MainComponent user={this.props.user}
                                   buttonStyle={this.props.buttonStyle}
                                   credentials={this.props.credentials}
                    ></MainComponent>
                )
            case 1:
                return(
                    <Projects user={this.props.user}
                              buttonStyle={this.props.buttonStyle}
                              credentials={this.props.credentials}
                              refresh={this.props.refresh}
                    ></Projects>
                )
            case 2:
                return(
                    <ReportedIssues user={this.props.user}
                                    buttonStyle={this.props.buttonStyle}
                                    credentials={this.props.credentials}
                    ></ReportedIssues>
                )
            case 3:
                return(
                    <AddIssue user={this.props.user}
                              buttonStyle={this.props.buttonStyle}
                              credentials={this.props.credentials}
                    ></AddIssue>
                )
            case 4:
                return(
                    <CalendarComponent
                        buttonStyle={this.props.buttonStyle}
                        credentials={this.props.credentials}
                    ></CalendarComponent>
                )
            case 5:
                return(
                    <Messages user={this.props.user}
                              buttonStyle={this.props.buttonStyle}
                              buttonStyleDisabled={this.props.buttonStyleDisabled}
                              credentials={this.props.credentials}
                    ></Messages>
                )
            case 6:
                return(
                    <NewUser buttonStyle={this.props.buttonStyle}
                             credentials={this.props.credentials}
                    />
                )
            case 7:
                return(
                    <MuiThemeProvider>
                        <div style={{display: 'block', textAlign: 'center'}}>
                            <CircularProgress />
                        </div>
                    </MuiThemeProvider>
                )
        }
    }
    render() {
        return (
            <div>
                {this.renderSwitch()}
            </div>
        );
    }
}

export default translate('common')(DisplayComponent);
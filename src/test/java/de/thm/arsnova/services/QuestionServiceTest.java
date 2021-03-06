/*
 * Copyright (C) 2012 THM webMedia
 * 
 * This file is part of ARSnova.
 *
 * ARSnova is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ARSnova is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.thm.arsnova.services;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.thm.arsnova.dao.StubDatabaseDao;
import de.thm.arsnova.entities.InterposedQuestion;
import de.thm.arsnova.exceptions.NotFoundException;
import de.thm.arsnova.exceptions.UnauthorizedException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:src/main/webapp/WEB-INF/spring/arsnova-servlet.xml",
		"file:src/main/webapp/WEB-INF/spring/spring-main.xml",
		"file:src/test/resources/test-config.xml"
})
public class QuestionServiceTest {

	@Autowired
	IQuestionService questionService;

	@Autowired
	StubUserService userService;
	
	@Autowired
	private StubDatabaseDao databaseDao;

	@After
	public final void cleanup() {
		databaseDao.cleanupTestData();
		userService.setUserAuthenticated(false);
	}

	@Test(expected = UnauthorizedException.class)
	public void testShouldNotReturnQuestionsIfNotAuthenticated() {
		userService.setUserAuthenticated(false);
		questionService.getSkillQuestions("12345678");

	}

	@Test(expected = NotFoundException.class)
	public void testShouldFindQuestionsForNonExistantSession() {
		userService.setUserAuthenticated(true);
		questionService.getSkillQuestions("00000000");
	}

	@Test
	public void testShouldFindQuestions() {
		userService.setUserAuthenticated(true);
		assertEquals(1, questionService.getSkillQuestionCount("12345678"));
	}
	
	@Test
	public void testShouldMarkInterposedQuestionAsReadIfSessionCreator() throws Exception {
		userService.setUserAuthenticated(true);
		InterposedQuestion theQ = new InterposedQuestion();
		theQ.setRead(false);
		theQ.set_id("the internal id");
		theQ.setSessionId("12345678");
		databaseDao.interposedQuestion = theQ;
		
		questionService.readInterposedQuestion(theQ.get_id());
		
		assertTrue(theQ.isRead());
	}
	
	@Test
	public void testShouldNotMarkInterposedQuestionAsReadIfRegularUser() throws Exception {
		userService.setUserAuthenticated(true, "regular user");
		InterposedQuestion theQ = new InterposedQuestion();
		theQ.setRead(false);
		theQ.set_id("the internal id");
		theQ.setSessionId("12345678");
		databaseDao.interposedQuestion = theQ;
		
		questionService.readInterposedQuestion(theQ.get_id());
		
		assertFalse(theQ.isRead());
	}
}
